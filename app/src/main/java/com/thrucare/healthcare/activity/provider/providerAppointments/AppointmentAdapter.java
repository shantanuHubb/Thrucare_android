package com.thrucare.healthcare.activity.provider.providerAppointments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.myAppointment.MyAppointmentsModel;
import com.thrucare.healthcare.activity.provider.ConfirmAppontmentProviderActivity;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    List<MyAppointmentsModel.Item> listData;
    AppointmentFragment ctx;
    SetOnclickProviderAppointment setOnclickProviderAppointment;

    public AppointmentAdapter(List<MyAppointmentsModel.Item> resObj, AppointmentFragment context) {
        this.listData = resObj;
        this.ctx = context;
        this.setOnclickProviderAppointment = (SetOnclickProviderAppointment) context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_provider_item, parent, false);

        return new AppointmentAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPatientName.setText(listData.get(position).getPatient().getFirstName() + " " +
                listData.get(position).getPatient().getMiddleName() + " " +
                listData.get(position).getPatient().getLastName() + " ");
        holder.tvPhonePatient.setText(listData.get(position).getPatient().getPhone());
        holder.tvStatusPatient.setText(listData.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPatientName, tvAgePatient, tvDateAppointment, tvPhonePatient, tvStatusPatient;
        LinearLayout llnProviderAppointment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvAgePatient = itemView.findViewById(R.id.tv_age_patient);
            tvDateAppointment = itemView.findViewById(R.id.tv_date_appointment_patient);
            tvPhonePatient = itemView.findViewById(R.id.tv_phone_number_patient);
            tvStatusPatient = itemView.findViewById(R.id.tv_status_appointment);
            llnProviderAppointment = itemView.findViewById(R.id.lln_provider_appoint_card);
            llnProviderAppointment.setOnClickListener(this);
            tvStatusPatient.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.lln_provider_appoint_card:
                    int position = getAdapterPosition();
                    setOnclickProviderAppointment.getPatientPosition(position,false);
                    break;
                case R.id.tv_status_appointment:
                    setOnclickProviderAppointment.getPatientPosition(getAdapterPosition(),true);
                    break;
            }
        }
    }

    public interface SetOnclickProviderAppointment {

        void getPatientPosition(int getAdapterPosition,Boolean isStatusClicked);
    }
}
