package com.example.unitrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {

    ImageView BtnLogout, BtnFeedback;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        BtnLogout = findViewById(R.id.BtnLogout);

        BtnFeedback = findViewById(R.id.feedback);


        mAuth=FirebaseAuth.getInstance();

        BtnLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(Setting.this, Login.class));
            overridePendingTransition(0, 0);
            Toast.makeText(Setting.this, "You've been logged out", Toast.LENGTH_SHORT).show();
        });


    }

    public void BackSet(View view) {
        finish();
        overridePendingTransition(0, 0);
    }

    public void BtnFeedback(View view) {
        startActivity(new Intent(Setting.this, Feedback.class));
        overridePendingTransition(0, 0);
    }
}