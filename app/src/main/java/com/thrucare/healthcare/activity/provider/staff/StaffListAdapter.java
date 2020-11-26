package com.thrucare.healthcare.activity.provider.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesAdapter;
import com.thrucare.healthcare.pojo.StaffList;

import java.util.List;

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.MyViewHolder> {
    List<StaffList.Item>  items  ;
    Context ctx;
    public StaffListAdapter(List<StaffList.Item> itemsList, Context context) {
        this.ctx = context;
        this.items  = itemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_item, parent, false);

        return new StaffListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvEmail.setText(items.get(position).getEmail());
        holder.tvName.setText(items.get(position).getFirstName());
        holder.tvPhone.setText(items.get(position).getPhone());
        StringBuilder sb  = new StringBuilder();
        for(int i =0 ; i<items.get(position).getRole().size() ; i++){
            if(i==0){
                sb.append(items.get(position).getRole().get(i).getDisplay());
            }else {
                sb.append(" , " +items.get(position).getRole().get(i).getDisplay());
            }

        }
        holder.tvRole.setText(sb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tvName ,  tvEmail , tvPhone , tvRole;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_staff_name);
            tvEmail = itemView.findViewById(R.id.tv_email_id_staff);
            tvPhone = itemView.findViewById(R.id.tv_phone_staff);
            tvRole = itemView.findViewById(R.id.tv_role_staff);
        }
    }
}
