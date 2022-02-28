package com.example.unitrack;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPost extends AppCompatActivity {

    private static  final int PICK_IMAGE_REQUEST = 1;
    private ImageView ButtonAddPhoto;
    private ImageView ButtonPost;
    private EditText Description;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ImageView Back;


    private Uri ImageUri;

    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;

    private StorageTask uploadTask;

    ActivityResultLauncher<Intent>activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ButtonAddPhoto = findViewById(R.id.addPhoto);
        ButtonPost = findViewById(R.id.post);
        Description = findViewById(R.id.write);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);
        Back = findViewById(R.id.ArrowBack);

        StorageRef = FirebaseStorage.getInstance().getReference("uploads");
        DatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        ButtonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });

        ButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddPost.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData().getData() != null){
                            ImageUri = result.getData().getData();

                            Picasso.with(AddPost.this).load(ImageUri).into(imageView);
                        }
                    }
                });
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (ImageUri != null){
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()+
                    "." + getFileExtension(ImageUri));

            uploadTask = fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(AddPost.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            /*Upload upload = new Upload(Description.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadID = DatabaseRef.push().getKey();
                            DatabaseRef.child(uploadID).setValue(upload);*/

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Upload upload = new Upload(Description.getText().toString().trim(),downloadUrl.toString());
                            String uploadId = DatabaseRef.push().getKey();
                            DatabaseRef.child(uploadId).setValue(upload);
                            startActivity(new Intent(AddPost.this, Admin_Dashboard.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void Setting(View view) {
        Intent intent = new Intent(AddPost.this, Setting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}