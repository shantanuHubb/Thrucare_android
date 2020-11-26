package com.thrucare.healthcare.activity.patient.providerReview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.ProviderReview;

import java.util.List;

public class ProviderReviewAdapter extends RecyclerView.Adapter<ProviderReviewAdapter.ViewHolder> {

    List<ProviderReview> list;
    Context ctx;

    public ProviderReviewAdapter(ProviderReviewActivity ctx, List<ProviderReview> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProviderReviewAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_review_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProviderReview providerReview = list.get(position);
        holder.tvName.setText(providerReview.getItems().get(position).getPatient().getFirstName()+" "+
                providerReview.getItems().get(position).getPatient().getMiddleName()+" "+
                providerReview.getItems().get(position).getPatient().getLastName());
        holder.tvDate.setText(providerReview.getItems().get(position).getReviewDate());
        holder.tvComment.setText(providerReview.getItems().get(position).getComment());
        holder.ratingBar.setRating(providerReview.getItems().get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvDate,tvComment;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_review_name);
            tvDate = itemView.findViewById(R.id.tv_review_date);
            tvComment = itemView.findViewById(R.id.tv_review_comment);
            ratingBar=itemView.findViewById(R.id.ratingBar);


        }
    }
}
