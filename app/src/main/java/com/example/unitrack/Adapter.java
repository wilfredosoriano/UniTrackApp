package com.example.unitrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    Context context;
    ArrayList<Document> list;
    DatabaseReference databaseRef;

    public Adapter(Context context, ArrayList<Document> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.document, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Document document = list.get(position);
        holder.document.setText(document.getDocument());
        holder.processID.setText(document.getId());
        holder.processAdd.setText(document.getAddress());
        holder.processName.setText(document.getName());



        holder.checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.processName.getContext());
                final EditText editText = new EditText(holder.processName.getContext());
                builder.setTitle("Confirm Request?");
                builder.setMessage("Enter date of claiming \ndd/mm/yyyy");
                builder.setView(editText);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String id = holder.processID.getText().toString();
                        String address = holder.processAdd.getText().toString();
                        String name = holder.processName.getText().toString();
                        String document = holder.document.getText().toString();

                        String date = editText.getText().toString();

                        databaseRef = FirebaseDatabase.getInstance().getReference().child("History");

                        AdminInformation adminInformation = new AdminInformation(id, address, name, document, date);

                        databaseRef.push().setValue(adminInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(context, "Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.processName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView document, processID, processAdd, processName ;
        ImageView checkBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            document = itemView.findViewById(R.id.TVHistory);
            processID = itemView.findViewById(R.id.ProcessID);
            processAdd = itemView.findViewById(R.id.ProcessAdd);
            processName = itemView.findViewById(R.id.ProcessName);

            checkBtn = itemView.findViewById(R.id.CheckBtn);

        }
    }

}
