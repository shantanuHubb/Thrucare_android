package com.thrucare.healthcare.activity.patient.medicalReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.medications.MedicationAdapter;
import com.thrucare.healthcare.pojo.MedicalReportList;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

public class MedicalReportAdapter extends RecyclerView.Adapter<MedicalReportAdapter.MyViewHolder> {
    private List<MedicalReportList.Item> list;
    private Context ctx;

    public MedicalReportAdapter(List<MedicalReportList.Item> resObjList, Context context) {
        this.ctx  = context;
        this.list  = resObjList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medical_report_item, parent, false);

        return new MedicalReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvCategory.setText(list.get(position).getCategory().getDisplay());
        holder.tvType.setText(list.get(position).getType().getDisplay());
        holder.tvOrganization.setText(list.get(position).getOrganization().getName());
        holder.tvEffectiveDate.setText(list.get(position).getEffectiveDate().substring(0 , 10));
        holder.tvDetailLink.setText(list.get(position).getAttachments().get(0).getUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory , tvType, tvOrganization, tvEffectiveDate, tvDetailLink;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory  = itemView.findViewById(R.id.tv_category_medical_report);
            tvType = itemView.findViewById(R.id.tv_type_medical_report);
            tvOrganization  = itemView.findViewById(R.id.tv_organization_medical_report);
            tvEffectiveDate  = itemView.findViewById(R.id.tv_effective_date);
            tvDetailLink  = itemView.findViewById(R.id.tv_detail_link_medical_report);
        }
    }
}
