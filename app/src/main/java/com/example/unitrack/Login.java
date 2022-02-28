package com.example.unitrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText etRegEmailAdd, etRegPassword;
    Button tvRegisterHere, BtnLogin;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    private long backPressedTime;
    private Toast backToast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etRegEmailAdd = findViewById(R.id.etRegEmailAdd);
        etRegPassword = findViewById(R.id.etRegPassword);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        BtnLogin = findViewById(R.id.BtnLogin);

        mAuth=FirebaseAuth.getInstance();
        mDatabase =FirebaseDatabase.getInstance();

        BtnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
            overridePendingTransition(0, 0);
        });

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void loginUser() {
        String email = etRegEmailAdd.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            etRegEmailAdd.setError("Please provide an email");
            etRegEmailAdd.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            etRegPassword.setError("Please provide your password");
            etRegPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String uid = task.getResult().getUser().getUid();

                        mDatabase.getReference("Users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int usertype = snapshot.getValue(Integer.class);

                                if (usertype == 0){
                                    Toast.makeText(Login.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, User_Dashboard.class));
                                }
                                if (usertype == 1){
                                    Toast.makeText(Login.this, "Admin Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, Admin_Dashboard.class));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        
                    }else{
                        Toast.makeText(Login.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}