package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register2 extends AppCompatActivity {

    EditText etRegName, etRegEmailAdd, etRegPassword, etRegAddress;
    Button tvLoginHere, BtnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        etRegName = findViewById(R.id.etRegName);
        etRegEmailAdd = findViewById(R.id.etRegEmailAdd);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegAddress = findViewById(R.id.etRegAddress);
        BtnRegister = findViewById(R.id.BtnRegister);
        tvLoginHere = findViewById(R.id.tvLoginHere);

        mAuth = FirebaseAuth.getInstance();

        BtnRegister.setOnClickListener(view -> {
            createUser();
        });
    }
    private void createUser() {
        String name = etRegName.getText().toString().trim();
        String email = etRegEmailAdd.getText().toString().trim();
        String address = etRegAddress.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        int usertype = 1;

        if (TextUtils.isEmpty(email)){
            etRegEmailAdd.setError("Please provide an email");
            etRegEmailAdd.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            etRegPassword.setError("Please provide your password");
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        String uid = task.getResult().getUser().getUid();
                        User user = new User(name, email, address, uid, usertype);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register2.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register2.this, Login.class));

                                }//closed bracket
                                else {
                                    Toast.makeText(Register2.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } //closed bracket
                            }
                        });

                    } // closed bracket
                    else {
                        Toast.makeText(Register2.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }
    }

    public void UserRegister(View view) {
        Intent intent = new Intent(Register2.this, Register.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}