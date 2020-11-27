package com.thrucare.healthcare.activity.patient.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.modelClasses.PatientEmergencyContactList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.MyViewHolder> {

    List<PatientEmergencyContactList.Item> itemList;
    Context ctx;

    public EmergencyContactAdapter(List<PatientEmergencyContactList.Item> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_item_list , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String ContactName = itemList.get(position).getFirstName() + " " +itemList.get(position).getMiddleName() + " "+ itemList.get(position).getLastName();
        holder.name.setText(ContactName);
        holder.email.setText(itemList.get(position).getEmail());
        holder.contact.setText(itemList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        if(itemList != null) {
            return itemList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , email , contact;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_emergency_name);
            email = itemView.findViewById(R.id.tv_emergency_email);
            contact = itemView.findViewById(R.id.tv_emergency_contact);

        }
    }
}
