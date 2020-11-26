package com.thrucare.healthcare.activity.provider.insurance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.qualification.QualificationListAdapter;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ProductInsuranceModel;
import com.thrucare.healthcare.pojo.modelClasses.PatientInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InsuranceListAdapter extends RecyclerView.Adapter<InsuranceListAdapter.MyViewHolder> {
    private List<ProviderInsuranceList.Item> itemListProvider;
    private List<PatientInsuranceList.Item> itemListPatient;
    private Context ctx;
    private MultipleProductAdapter mAdapter;


    public InsuranceListAdapter(List<ProviderInsuranceList.Item> itemList, Context ctx , List<PatientInsuranceList.Item> itemListPatient) {
        this.itemListProvider = itemList;
        this.ctx = ctx;
        this.itemListPatient = itemListPatient;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insurance_list_item, parent, false);

        return new InsuranceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int HeaderPosition = position +1;
        holder.tvInsuranceHeading.setText("Insurance " + HeaderPosition);

        if(itemListProvider != null)
        {
            holder.tvPayer.setText(itemListProvider.get(position).getPayer().getDisplay());
            List<ProviderInsuranceList.Product> productList = itemListProvider.get(position).getPayer().getProduct();
            mAdapter = new MultipleProductAdapter(productList , ctx);
        }
       else {
            holder.tvPayer.setText(itemListPatient.get(position).getPayer().getDisplay());
            List<PatientInsuranceList.Product> productList = itemListPatient.get(position).getPayer().getProduct();
            mAdapter = new MultipleProductAdapter(productList , ctx , "");
        }



        holder.recyclerViewProduct.setLayoutManager(holder.layoutManager);
        holder.recyclerViewProduct.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if(itemListProvider != null)
        {
            return itemListProvider.size();
        }
        else
            return itemListPatient.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPayer  , tvInsuranceHeading;
        RecyclerView recyclerViewProduct;
        LinearLayoutManager layoutManager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPayer  = itemView.findViewById(R.id.tv_payer_insurance);

            tvInsuranceHeading = itemView.findViewById(R.id.tv_insurance_item_heading);
            recyclerViewProduct = itemView.findViewById(R.id.recycler_Product_insurnce);
            layoutManager = new LinearLayoutManager(ctx);
        }
    }
}
