package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Register extends AppCompatActivity {

    EditText etRegName, etRegEmailAdd, etRegPassword, etRegAddress;
    Button tvLoginHere, BtnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

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
                    if (task.isSuccessful()) {
                        Admin admin = new Admin(name, email, address);

                        FirebaseDatabase.getInstance().getReference("Admin")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Admin_Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Admin_Register.this, Login.class));

                                }//closed bracket
                                else {
                                    Toast.makeText(Admin_Register.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } //closed bracket
                            }
                        });
                    } else {
                        Toast.makeText(Admin_Register.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}