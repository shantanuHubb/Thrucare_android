package com.thrucare.healthcare.activity.patient.medications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.MedicationPojp;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

    private List<MedicationPojp> dataList;
    private Context ctx;
    private SetOnClickOnMedicationView setOnClickOnMedicationView;


    public MedicationAdapter(List<MedicationPojp> dataList, Context context, MedicationFragment medicationFragment) {
        this.dataList = dataList;
        this.ctx  = context;
        this.setOnClickOnMedicationView  = (SetOnClickOnMedicationView) medicationFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(dataList.get(position).getTvName());
        holder.tvReason.setText(dataList.get(position).getTvReason());
        holder.tvDate.setText(dataList.get(position).getTvDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvReason, tvDate;
        CardView crdMedication;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName  = itemView.findViewById(R.id.tv_name);
            tvReason  = itemView.findViewById(R.id.tv_reasons);
            tvDate  = itemView.findViewById(R.id.tv_date);
            crdMedication  = itemView.findViewById(R.id.crd_medication_add);
            crdMedication.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case  R.id.crd_medication_add:
                    setOnClickOnMedicationView.onClick(getAdapterPosition());
                    break;

            }
        }
    }

    public interface SetOnClickOnMedicationView{
        void onClick(int position);
    }
}
