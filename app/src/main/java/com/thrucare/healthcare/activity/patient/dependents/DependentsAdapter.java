package com.thrucare.healthcare.activity.patient.dependents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesAdapter;
import com.thrucare.healthcare.pojo.DependentsList;

import java.util.List;

public class DependentsAdapter extends RecyclerView.Adapter<DependentsAdapter.MyViewHolder> {
      List<DependentsList.Item> items;
      Context ctx;

    public DependentsAdapter(List<DependentsList.Item> itemList, Context context) {
        this.ctx  = context;
        this.items  = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dependents_list_item, parent, false);

        return new DependentsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvDependentsName.setText(items.get(position).getFirstName()
                              + " "+items.get(position).getMiddleName() + " "+items.get(position).getLastName());
        holder.tvDependentsEmail.setText(items.get(position).getEmail());
        holder.tvDependentsGender.setText(items.get(position).getGender().getDisplay());
        holder.tvDependentsPhone.setText(items.get(position).getPhone());
        holder.tvDependentsDOB.setText(items.get(position).getBirthDate().substring(0 ,10));
        holder.tvDependentsRelationName.setText(items.get(position).getResponsiblePerson().getRelation());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDependentsName, tvDependentsEmail, tvDependentsGender, tvDependentsPhone, tvDependentsDOB, tvDependentsRelationName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDependentsName  = itemView.findViewById(R.id.tv_name_dependents);
            tvDependentsEmail  = itemView.findViewById(R.id.tv_email_dependents);
            tvDependentsGender  = itemView.findViewById(R.id.tv_gender_dependents);
            tvDependentsPhone  = itemView.findViewById(R.id.tv_phone_dependents);
            tvDependentsDOB  = itemView.findViewById(R.id.tv_dob_dependents);
            tvDependentsRelationName  = itemView.findViewById(R.id.tv_relation_dependents);
        }
    }
}
