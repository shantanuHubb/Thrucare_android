package com.thrucare.healthcare.activity.patient.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.bookAppointment.ProviderDetailActivity;
import com.thrucare.healthcare.pojo.OrigazationSearch;

import java.util.List;

public class HomeProviderListAdapter extends
        RecyclerView.Adapter<HomeProviderListAdapter.MyViewHolderProvider> {

    List<OrigazationSearch.ResponseOrganizationSearch> list;
    Context ctx;
    int status;


    public HomeProviderListAdapter(List<OrigazationSearch.ResponseOrganizationSearch> resObj, Context context , int status) {
        this.status  = status;
        this.ctx = context;
        this.list = resObj;
    }

    @NonNull
    @Override
    public MyViewHolderProvider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.provider_list_item, parent, false);

        return new HomeProviderListAdapter.MyViewHolderProvider(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderProvider holder, int position) {
        if(status==0){
            holder.tvProviderSelect.setVisibility(View.GONE);
            holder.tvProviderFive.setVisibility(View.VISIBLE);
            holder.tvProviderListName.setText("John Provider");
            holder.tvProviderTwo.setText("Posttraumatic stress disorder");
            holder.tvProviderThree.setText("Psychotherapy");
            holder.tvProviderFour.setText("Psychiatry");
            holder.tvProviderFive.setText("Multispeciality Dental Clinic");

        }else {
            holder.tvProviderListName.setText(list.get(position).getName());
            holder.tvProviderTwo.setText(list.get(position).getEmail());
            holder.tvProviderThree.setText(list.get(position).getPhone());
            holder.tvProviderFour.setText(list.get(position).getAddress().getLine1() + " " + list.get(position).getAddress().getLine2()
                    +" "+list.get(position).getAddress().getCity() + " " + list.get(position).getAddress().getCity() + " " +
                    list.get(position).getAddress().getCountry());
        }


    }

    @Override
    public int getItemCount() {
        if(status==0){
            return 1;
        }else {
            return list.size();
        }

    }

    public class MyViewHolderProvider extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvProviderListName, tvProviderTwo, tvProviderThree, tvProviderFour, tvProviderFive , tvProviderSelect;

        CardView tvCardProviderList;
        public MyViewHolderProvider(@NonNull View itemView) {
            super(itemView);
            tvProviderListName = itemView.findViewById(R.id.tv_provider_name_one);
            tvProviderTwo = itemView.findViewById(R.id.tv_provider_two);
            tvProviderThree = itemView.findViewById(R.id.tv_provider_three);
            tvProviderFour = itemView.findViewById(R.id.tv_provider_four);
            tvProviderFive = itemView.findViewById(R.id.tv_provider_five);
            tvProviderSelect = itemView.findViewById(R.id.tv_provider_select);
            tvCardProviderList = itemView.findViewById(R.id.card_provider_list);
            tvCardProviderList.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.card_provider_list:
                    ctx.startActivity(new Intent(ctx, ProviderDetailActivity.class));
                    break;
            }
        }
    }
}
