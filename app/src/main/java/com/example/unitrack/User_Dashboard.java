package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import java.util.List;

public class User_Dashboard extends AppCompatActivity {

    //Button BtnLogout;
    FirebaseAuth mAuth;
    RelativeLayout relativeSheet;
    TextView btnCog, btnEnrol, btnCoc;

    private RecyclerView mRecyclerView;
    private ImageAdapter imageAdapter;


    private DatabaseReference DatabaseRef;
    private List<Upload> Uploads;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //BtnLogout=findViewById(R.id.BtnLogout);
        mAuth = FirebaseAuth.getInstance();

       // BtnLogout.setOnClickListener(view -> {
        //    mAuth.signOut();
          //  startActivity(new Intent(User_Dashboard.this, Login.class));
        //});


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Uploads = new ArrayList<>();
        
        DatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    Uploads.add(upload);
                }
                imageAdapter = new ImageAdapter(User_Dashboard.this, Uploads);
                mRecyclerView.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        relativeSheet=findViewById(R.id.relative_sheet);
        BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(relativeSheet);

        btnCog=findViewById(R.id.BtnCOG);
        btnEnrol=findViewById(R.id.BtnEnrol);
        btnCoc=findViewById(R.id.BtnCOC);

        btnCog.setOnClickListener(view ->{
            btnCog();
        });
        btnEnrol.setOnClickListener(view ->{
            btnEnrol();
        });
        btnCoc.setOnClickListener(view ->{
            btnCoc();
        });



    }

    private void btnCoc() {
        startActivity(new Intent(User_Dashboard.this, User_Form3.class));
        overridePendingTransition(0, 0);
    }

    private void btnEnrol() {
        startActivity(new Intent(User_Dashboard.this, User_Form.class));
        overridePendingTransition(0, 0);
    }

    private void btnCog() {
        startActivity(new Intent(User_Dashboard.this, User_Form2.class));
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user==null){
            startActivity(new Intent(User_Dashboard.this, Login.class));
        }
    }
    public void Profile(View view) {
        Intent intent= new Intent(User_Dashboard.this, Profile.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void Setting(View view) {
        Intent intent = new Intent(User_Dashboard.this, Setting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}