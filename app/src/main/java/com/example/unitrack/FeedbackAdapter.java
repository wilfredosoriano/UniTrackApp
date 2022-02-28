package com.example.unitrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    Context ctx;
    ArrayList<UserFeedback> feedbackArrayList;

    public FeedbackAdapter(Context ctx, ArrayList<UserFeedback> feedbackArrayList) {
        this.ctx = ctx;
        this.feedbackArrayList = feedbackArrayList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.feedback, parent, false);
        return new FeedbackAdapter.FeedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {

        UserFeedback userFeedback = feedbackArrayList.get(position);
        holder.tvFeedback.setText(userFeedback.getFeedback());
        holder.tvRating.setText(userFeedback.getStar());

    }

    @Override
    public int getItemCount() {
        return feedbackArrayList.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder{

        TextView tvFeedback, tvRating;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFeedback = itemView.findViewById(R.id.TVFeedback);
            tvRating = itemView.findViewById(R.id.Ratings);
        }
    }
}
