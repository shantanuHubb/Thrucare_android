package com.thrucare.healthcare.activity.patient.bookAppointment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.myAppointment.PatientAppointmentAdapter;
import com.thrucare.healthcare.pojo.ServiceAssigment;

import java.util.List;

public class StepOneBookAppointmentAdapter extends RecyclerView.Adapter<StepOneBookAppointmentAdapter.MyViewHolder> {
    Context ctx;
    List<ServiceAssigment.Item> itemsList;


    public StepOneBookAppointmentAdapter(List<ServiceAssigment.Item> items, Context context) {
     this.ctx  = context;
     this.itemsList  = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_assignment_item, parent, false);

        return new StepOneBookAppointmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.tvLocation.setText(itemsList.get(position).getLocation().getName());
       StringBuilder  sb  = new StringBuilder();
       for(int i =0 ; i<itemsList.get(position).getService().getType().size() ; i++){
           if(i==0){
             sb.append(itemsList.get(position).getService().getType().get(i).getDisplay());
           }else {
               sb.append(" ,"+itemsList.get(position).getService().getType().get(i).getDisplay());

           }
       }
       holder.tvServiceType.setText(sb);

       holder.tvDate.setText("5.30 Am  , Wednesday");
        StringBuilder  sb1  = new StringBuilder();
        for(int i =0 ; i<itemsList.get(position).getSpeciality().size() ; i++){
            if(i==0){
                sb1.append(itemsList.get(position).getSpeciality().get(i).getDisplay());
            }else {
                sb1.append(" , "+itemsList.get(position).getSpeciality().get(i).toString());

            }
        }
        holder.tvSpeciality.setText(sb1);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvLocation, tvServiceType, tvDate, tvSpeciality;
        LinearLayout llnSelectLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLocation = itemView.findViewById(R.id.tv_location_name);
            tvServiceType = itemView.findViewById(R.id.tv_service_type_service_assignment);
            tvDate = itemView.findViewById(R.id.tv_date_time_service_assignment);
            tvSpeciality = itemView.findViewById(R.id.tv_speciality_service_assignment);
            llnSelectLayout = itemView.findViewById(R.id.lln_select_service_assignment);
            llnSelectLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lln_select_service_assignment:
                    ctx.startActivity(new Intent(ctx, ScheduleAppointmentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
            }
        }
    }
}
