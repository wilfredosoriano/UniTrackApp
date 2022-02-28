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

public class Admin_Login extends AppCompatActivity {

    EditText etRegEmailAdd, etRegPassword;
    Button BtnLogin;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etRegEmailAdd = findViewById(R.id.etRegEmailAdd);
        etRegPassword = findViewById(R.id.etRegPassword);
        BtnLogin = findViewById(R.id.BtnLogin);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();

        BtnLogin.setOnClickListener(view ->{
            loginAdmin();
        });

    }

    private void loginAdmin() {
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
                        Toast.makeText(Admin_Login.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_Login.this, Admin_Dashboard.class));
                    }else{
                        Toast.makeText(Admin_Login.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void btnUser(View view) {
        startActivity(new Intent(Admin_Login.this, Login.class));
    }
}