package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbReference;

    String userID;
    TextView txtDisplay;
    TextView NameDisplay;

    RelativeLayout profileSheet;

    DatabaseReference NewDatabase;
    RecyclerView NewRecyclerView;
    NewAdapter MyNewAdapter;
    ArrayList<History> HistoryList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profileSheet=findViewById(R.id.profile_sheet);
        BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(profileSheet);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        txtDisplay = findViewById(R.id.TextView);
        NameDisplay = findViewById(R.id.Name);

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    String id, name, email, address;

                    name = user.name;
                    email = user.email;
                    address = user.address;

                    txtDisplay.setText("Address:\n "+address+"\n\nEmail:\n "+email);
                    NameDisplay.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

        NewRecyclerView = findViewById(R.id.HList);


        NewDatabase = FirebaseDatabase.getInstance().getReference("History");


        NewRecyclerView.setHasFixedSize(true);
        NewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryList = new ArrayList<>();
        MyNewAdapter = new NewAdapter(this, HistoryList);
        NewRecyclerView.setAdapter(MyNewAdapter);

        NewDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    History history = dataSnapshot.getValue(History.class);
                    HistoryList.add(history);
                }
                MyNewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void Setting(View view) {
        Intent intent = new Intent(Profile.this, Setting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void Back(View view) {
        finish();
        overridePendingTransition(0, 0);
    }
}