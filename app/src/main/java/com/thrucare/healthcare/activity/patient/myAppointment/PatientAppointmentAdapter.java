package com.thrucare.healthcare.activity.patient.myAppointment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;

import java.util.List;

public class PatientAppointmentAdapter extends RecyclerView.Adapter<PatientAppointmentAdapter.MyViewHolder> {
    List<MyAppointmentsModel.Item> list;
    MyAppointmentFragment ctx;
    SetOnClickOnSetting setOnClickOnSetting;

    public PatientAppointmentAdapter(List<MyAppointmentsModel.Item> resObj,
                                     MyAppointmentFragment activity) {
        this.ctx = activity;
        this.list = resObj;
        this.setOnClickOnSetting = (SetOnClickOnSetting) activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_appointment_item, parent, false);

        return new PatientAppointmentAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvProviderName.setText(list.get(position).getServiceAssignment().getProvider().getFirstName() + " "
                + list.get(position).getServiceAssignment().getProvider().getMiddleName() + " " + list.get(position).getServiceAssignment().getProvider().getLastName());

        holder.tvLocationName.setText(list.get(position).getServiceAssignment().getLocation().getName());
        holder.tvFullAddress.setText(list.get(position).getServiceAssignment().getLocation().getAddress().getLine1() + "" +
                list.get(position).getServiceAssignment().getLocation().getAddress().getLine2() + " " +
                list.get(position).getServiceAssignment().getLocation().getAddress().getCity() + " " +
                list.get(position).getServiceAssignment().getLocation().getAddress().getState() + " " +
                " " +
                list.get(position).getServiceAssignment().getLocation().getAddress().getCountry());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProviderName, tvDateTime, tvLocationName, tvFullAddress;
        LinearLayout llnSettingThree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProviderName = itemView.findViewById(R.id.tv_provider_name);
            tvDateTime = itemView.findViewById(R.id.tv_date_time_myAppointment);
            tvLocationName = itemView.findViewById(R.id.tv_location_name);
            tvFullAddress = itemView.findViewById(R.id.tv_full_address);
            llnSettingThree = itemView.findViewById(R.id.lln_setting_three_dot_patient_appointment);
            llnSettingThree.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.lln_setting_three_dot_patient_appointment:
                    int adapterPosition = getAdapterPosition();
                    setOnClickOnSetting.openBottomSheet(adapterPosition);
                    break;
            }
        }
    }

    public interface SetOnClickOnSetting{

        void openBottomSheet(int adapterPosition);
    }
}
