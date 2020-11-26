package com.thrucare.healthcare.activity.patient.diagnosis;

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

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.MyViewHolder> {

    private List<MedicationPojp> dataList;
    private Context ctx;
    private SetOnCardClickListener setOnCardClickListener;


    public DiagnosisAdapter(List<MedicationPojp> dataList, Context context, DiagnosisFragment diagnosisFragment) {
        this.dataList = dataList;
        this.ctx  = context;
        this.setOnCardClickListener  = (SetOnCardClickListener) diagnosisFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diagnosis_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(dataList.get(position).getTvName().length()>25){
            holder.tvName.setText(dataList.get(position).getTvName().substring(0, 25)+"...");
        }else {
            holder.tvName.setText(dataList.get(position).getTvName());
        }

        holder.tvReason.setText(dataList.get(position).getTvReason());
        holder.tvDate.setText(dataList.get(position).getTvDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvReason, tvDate;
        CardView crdDiagnosis;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName  = itemView.findViewById(R.id.tv_name);
            tvReason  = itemView.findViewById(R.id.tv_reasons);
            tvDate  = itemView.findViewById(R.id.tv_date);
            crdDiagnosis  = itemView.findViewById(R.id.card_diagnosis_add);
            crdDiagnosis.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.card_diagnosis_add:
                     setOnCardClickListener.onClick(getAdapterPosition());
                    break;

            }
        }
    }

    public interface SetOnCardClickListener{
        void onClick(int adapterPosition);
    }
}
