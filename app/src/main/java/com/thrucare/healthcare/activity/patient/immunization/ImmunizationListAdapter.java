package com.thrucare.healthcare.activity.patient.immunization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.vision.VisionListAdapter;
import com.thrucare.healthcare.pojo.ImmunizationList;

import java.util.List;

public class ImmunizationListAdapter extends RecyclerView.Adapter<ImmunizationListAdapter.MyViewHolder> {

    List<ImmunizationList.Item> items;
    ImmunizationFragment ctx;
    SetOnClickListenerOnImmunization setOnClickListenerOnImmunization;

    public ImmunizationListAdapter(List<ImmunizationList.Item> items, ImmunizationFragment immunizationFragment) {
        this.ctx = immunizationFragment;
        this.items = items;
        this.setOnClickListenerOnImmunization  = (SetOnClickListenerOnImmunization) immunizationFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.immunization_list_item, parent, false);

        return new ImmunizationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvVaccineName.setText(items.get(position).getVaccine().getDisplay());
        holder.tvOriginName.setText(items.get(position).getOrigin().getDisplay());
        holder.tvImmunization.setText(items.get(position).getOccurDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvVaccineName, tvOriginName, tvImmunization;
        CardView cardImmunization;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVaccineName = itemView.findViewById(R.id.tv_vaccine_name);
            tvOriginName = itemView.findViewById(R.id.tv_origin_name);
            tvImmunization = itemView.findViewById(R.id.tv_date_immunization);
            cardImmunization = itemView.findViewById(R.id.card_immunization_data);
            cardImmunization.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card_immunization_data:
                     setOnClickListenerOnImmunization.onClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface SetOnClickListenerOnImmunization{
        void onClick(int position);
    }
}
