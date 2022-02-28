package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ratings extends AppCompatActivity {

    DatabaseReference RateDatabase;
    RecyclerView feedbackRecyclerView;
    FeedbackAdapter feedbackAdapter;
    ArrayList<UserFeedback> feedbackArrayList;
    ImageView btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        feedbackRecyclerView = findViewById(R.id.RatingList);


        RateDatabase = FirebaseDatabase.getInstance().getReference("Feedbacks");


        feedbackRecyclerView.setHasFixedSize(true);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedbackArrayList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(this, feedbackArrayList);
        feedbackRecyclerView.setAdapter(feedbackAdapter);

        RateDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserFeedback userFeedback = dataSnapshot.getValue(UserFeedback.class);
                    feedbackArrayList.add(userFeedback);
                }
                feedbackAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDelete = findViewById(R.id.delete_feedbacks);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Ratings.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Data will be deleted permanently.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Feedbacks")
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Ratings.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }else{
                                    Toast.makeText(Ratings.this, "Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Ratings.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    public void BackBtn(View view) {
        finish();
        overridePendingTransition(0, 0);
    }
}