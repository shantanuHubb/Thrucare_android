package com.thrucare.healthcare.activity.provider.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thrucare.healthcare.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProviderServiceAdapter extends RecyclerView.Adapter<ProviderServiceAdapter.MyViewHolder> {

    private List<ProviderServiceList.Item> itemList;
    private Context ctx;

    public ProviderServiceAdapter(List<ProviderServiceList.Item> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.service_item_list, parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvRole.setText(itemList.get(position).getRole().get(position).getDisplay());
        holder.tvSpeciality.setText(itemList.get(position).getSpeciality().get(position).getDisplay());
        holder.tvPeriodStart.setText(itemList.get(position).getPeriod().getStart().split("T")[0]);
        holder.tvPeriodEnd.setText(itemList.get(position).getPeriod().getEnd().split("T")[0]);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRole , tvSpeciality , tvPeriodStart , tvPeriodEnd;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRole = itemView.findViewById(R.id.tv_role);
            tvSpeciality = itemView.findViewById(R.id.tv_speciality);
            tvPeriodStart = itemView.findViewById(R.id.tv_date_start);
            tvPeriodEnd = itemView.findViewById(R.id.tv_date_end);
        }
    }
}
