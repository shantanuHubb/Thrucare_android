package com.thrucare.healthcare.activity.patient.deviceOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.diagnosis.DiagnosisAdapter;
import com.thrucare.healthcare.pojo.DeviceList;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder> {
    List<DeviceList.Item> itemList ;
    Context ctx;
    public DeviceListAdapter(List<DeviceList.Item> itemList , DeviceOrderActivity activity){
      this.ctx = activity;
      this.itemList  =itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_item, parent, false);

        return new DeviceListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDeviceName.setText(itemList.get(position).getProvider().getFirstName() +" "+
                itemList.get(position).getProvider().getMiddleName() +" "+
                itemList.get(position).getProvider().getLastName());

        holder.tvReasonName.setText(itemList.get(position).getReason().get(0).getDisplay());
        holder.tvTypeDevice.setText(itemList.get(position).getType().getDisplay());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
           TextView tvDeviceName, tvTypeDevice, tvReasonName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDeviceName  = itemView.findViewById(R.id.tv_name_device);
            tvTypeDevice  = itemView.findViewById(R.id.tv_type_device);
            tvReasonName  = itemView.findViewById(R.id.tv_reason_device);

        }
    }
}
