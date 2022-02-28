package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    EditText etFeedback;
    DatabaseReference dbReference;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        dbReference = FirebaseDatabase.getInstance().getReference().child("Feedbacks");
        etFeedback = findViewById(R.id.etFeedback);

        ratingBar = findViewById(R.id.rating_bar);
    }

    public void BackSet(View view) {
        finish();
        overridePendingTransition(0, 0);
    }

    public void SubmitFeedback(View view) {

        if (TextUtils.isEmpty(etFeedback.getText().toString())) {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_SHORT).show();
        }else {

            String feedback = etFeedback.getText().toString().trim();
            String star = String.valueOf(ratingBar.getRating());

            FeedBackInformation feedBackInformation = new FeedBackInformation(feedback, star);

            dbReference.push().setValue(feedBackInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Feedback.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                        etFeedback.getText().clear();
                    } else {
                        Toast.makeText(Feedback.this, "Failed to Submit" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}