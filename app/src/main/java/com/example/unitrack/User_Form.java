package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class User_Form extends AppCompatActivity {

    EditText editStudentID, editStudentAdd, editStudentName;
    DatabaseReference userRef;
    TextView tvEnrolmentForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        editStudentID=findViewById(R.id.etStudentID);
        editStudentAdd=findViewById(R.id.etStudentAdd);
        editStudentName=findViewById(R.id.etStudentName);
        tvEnrolmentForm = findViewById(R.id.TVEnrolmentForm);

        userRef = FirebaseDatabase.getInstance().getReference().child("Request");


    }

    public void Back(View view) {
        Intent intent = new Intent(User_Form.this, User_Dashboard.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void Setting(View view) {
        Intent intent = new Intent(User_Form.this, Setting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void BtnSubmit(View view) {
        check();
    }

    private void check() {
        if (TextUtils.isEmpty(editStudentID.getText().toString())){
            Toast.makeText(this, "Please provide your Student ID.",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(editStudentAdd.getText().toString())){
            Toast.makeText(this, "Please provide your Address.",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(editStudentName.getText().toString())){
            Toast.makeText(this, "Please provide your Name.",Toast.LENGTH_SHORT).show();
        }else {
            ConfirmRequest();
        }
    }

    private void ConfirmRequest() {


        String id = editStudentID.getText().toString().trim();
        String address = editStudentAdd.getText().toString().trim();
        String name = editStudentName.getText().toString().trim();
        String document = tvEnrolmentForm.getText().toString();

        UserInformation userInformation = new UserInformation(id, address, name, document);

        userRef.push().setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(User_Form.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                    editStudentID.getText().clear();
                    editStudentAdd.getText().clear();
                    editStudentName.getText().clear();
                }else{
                    Toast.makeText(User_Form.this, "Failed to Submit" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}