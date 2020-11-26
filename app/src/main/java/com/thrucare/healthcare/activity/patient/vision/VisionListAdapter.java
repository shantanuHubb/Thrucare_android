package com.thrucare.healthcare.activity.patient.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.order.OrderSubModuleAdapter;
import com.thrucare.healthcare.pojo.VisionList;

import java.util.List;

public class VisionListAdapter extends RecyclerView.Adapter<VisionListAdapter.MyViewHolder> {
      List<VisionList.Item> list;
      Context ctx;
    public VisionListAdapter(List<VisionList.Item> items, VisionListActivity visionListActivity) {
        this.ctx  = visionListActivity;
        this.list  = items;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vision_list_item, parent, false);

        return new VisionListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.tvProviderName.setText(list.get(position).getProvider().getFirstName()+  " " +
              list.get(position).getProvider().getMiddleName()+  " " +
              list.get(position).getProvider().getLastName()+  " " );

      holder.tvProductName.setText(list.get(position).getSpecification().get(0).getProduct());
      holder.tvDateVision.setText(list.get(position).getCreatedDate().substring(0 , 10));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
         TextView tvProviderName , tvProductName, tvDateVision;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProviderName  = itemView.findViewById(R.id.tv_provider_name_vision);
            tvProductName  = itemView.findViewById(R.id.tv_product_name_vision);
            tvDateVision  = itemView.findViewById(R.id.tv_date_vision);
        }
    }
}
