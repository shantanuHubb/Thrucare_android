package com.thrucare.healthcare.activity.provider.insurance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.modelClasses.PatientInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderInsuranceList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleProductAdapter extends RecyclerView.Adapter<MultipleProductAdapter.MyViewHolder> {

    private List<ProviderInsuranceList.Product> itemListProvider;
    private List<PatientInsuranceList.Product> itemListPatient;
    private Context ctx;

    public MultipleProductAdapter(List<ProviderInsuranceList.Product> itemList, Context ctx) {
        this.itemListProvider = itemList;
        this.ctx = ctx;
    }

    public MultipleProductAdapter(List<PatientInsuranceList.Product> productList, Context ctx , String s) {
        this.itemListPatient = productList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_product_list, parent, false);

        return new MultipleProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(itemListProvider != null)
        holder.tvMultipleProduct.setText(itemListProvider.get(position).getDisplay());
        else
            holder.tvMultipleProduct.setText(itemListPatient.get(position).getDisplay());
    }

    @Override
    public int getItemCount() {
        if(itemListProvider != null)
        return itemListProvider.size();
        else
        {
            return itemListPatient.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMultipleProduct;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMultipleProduct = itemView.findViewById(R.id.tv_multiple_product);
        }
    }
}
