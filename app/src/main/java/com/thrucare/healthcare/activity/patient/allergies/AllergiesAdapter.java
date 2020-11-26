package com.thrucare.healthcare.activity.patient.allergies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.AllergiesInternalModel;

import java.util.List;

public class    AllergiesAdapter  extends RecyclerView.Adapter<AllergiesAdapter.MyViewHolder> {

    List<AllergiesInternalModel> dataList;
    Context ctx;
    SetOnClickListenerOnAllergies setOnClickListenerOnAllergies;

    public AllergiesAdapter(List dataList, Context context, AllergiesFragment allergiesFragment) {
        this.dataList = dataList;
        this.ctx = context;
        this.setOnClickListenerOnAllergies  =( SetOnClickListenerOnAllergies) allergiesFragment;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allergiesitem, parent, false);

        return new AllergiesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          // holder.tvDocName.setText(dataList.get(position).getTvDocName());
           holder.tvCritically.setText(dataList.get(position).getCricilityName());
           holder.tvSubstance.setText(dataList.get(position).getSubtanceName());
           holder.tvOnsetDate.setText(dataList.get(position).getOnSetDate());
           holder.tvSeverity.setText(dataList.get(position).getTvDocName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDocName, tvCategory, tvSubstance , tvCritically , tvSeverity,tvOnsetDate ;
        CardView cardAllergies;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDocName  = itemView.findViewById(R.id.tv_doc_name);
            tvSubstance  = itemView.findViewById(R.id.tv_substance);
            tvCritically  = itemView.findViewById(R.id.tv_criticality);
            tvSeverity  = itemView.findViewById(R.id.tv_severity);
            tvOnsetDate  = itemView.findViewById(R.id.tv_onsetDate);
            cardAllergies  = itemView.findViewById(R.id.crd_allergies_data);
            cardAllergies.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.crd_allergies_data:
                     setOnClickListenerOnAllergies.onClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface SetOnClickListenerOnAllergies{
        void onClick (int position);

    }
}
