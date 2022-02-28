package com.example.unitrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder>{

    Context NewContext;
    ArrayList<History> HistoryList;

    public NewAdapter(Context newContext, ArrayList<History> historyList) {
        this.NewContext = newContext;
        this.HistoryList = historyList;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(NewContext).inflate(R.layout.history, parent, false);
        return new NewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {

        History history = HistoryList.get(position);
        holder.historyDocument.setText(history.getDocument());
        holder.historyID.setText(history.getId());
        holder.historyAdd.setText(history.getAddress());
        holder.historyName.setText(history.getName());
        holder.historyDate.setText(history.getDate());

    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{

        TextView historyDocument, historyID, historyAdd, historyName, historyDate ;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);

            historyDocument = itemView.findViewById(R.id.TxtHistory);
            historyID = itemView.findViewById(R.id.HistoryID);
            historyAdd = itemView.findViewById(R.id.HistoryAdd);
            historyName = itemView.findViewById(R.id.HistoryName);
            historyDate = itemView.findViewById(R.id.HistoryDate);
        }
    }
}
