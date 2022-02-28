package com.example.unitrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminSetting extends AppCompatActivity {
    ImageView BtnLogout, BtnFdback, BtnBack;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);

        BtnBack=findViewById(R.id.ArrowBack);
        BtnLogout = findViewById(R.id.BtnLogout);
        BtnFdback = findViewById(R.id.user_feedback);

        mAuth=FirebaseAuth.getInstance();

        BtnLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(AdminSetting.this, Login.class));
            overridePendingTransition(0, 0);
            Toast.makeText(AdminSetting.this, "You've been logged out", Toast.LENGTH_SHORT).show();
        });

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        BtnFdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminSetting.this, Ratings.class));
                overridePendingTransition(0, 0);
            }
        });
    }

}