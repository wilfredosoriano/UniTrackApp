package com.example.unitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Admin_Dashboard extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    FloatingActionButton floatingActionButton;
    private RecyclerView mRecyclerView;
    private ImageAdapter imageAdapter;

    private ValueEventListener DBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference DatabaseRef;
    private List<Upload> Uploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Uploads = new ArrayList<>();

        imageAdapter = new ImageAdapter(Admin_Dashboard.this, Uploads);
        mRecyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(Admin_Dashboard.this);
        
        mStorage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                Uploads.clear();
                
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    Uploads.add(upload);
                }
                imageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin_Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:


                    case R.id.process:
                        startActivity(new Intent(getApplicationContext(),ViewOrderProcess.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),ViewHistory.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return true;
            }
        });



        floatingActionButton = findViewById(R.id.floatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Dashboard.this, AddPost.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        //Toast.makeText(this, "Normal click at position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = Uploads.get(position);
        String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(Admin_Dashboard.this, "Post Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseRef.removeEventListener(DBListener);
    }

    public void Setting(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, AdminSetting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}