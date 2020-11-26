package com.thrucare.healthcare.activity.provider.quotation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.ResponseQuotation;

import java.util.List;

public class QuotationListAdapter extends RecyclerView.Adapter<QuotationListAdapter.MyViewHolder> {

    private List<ResponseQuotation.Item> dataList ;
    Context ctx;
    public QuotationListAdapter(List<ResponseQuotation.Item> itemList, QuotationListActivity quotationListActivity) {
        this.ctx  = quotationListActivity;
        this.dataList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_item, parent, false);
        return new QuotationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.tvCreatedAt.setText("Created At :- "+dataList.get(position).getCreatedDate().substring(0 , 10));
      holder.tvStatus.setText("Status :- "+dataList.get(position).getStatus().getCode());
      holder.tvPrice.setText("Price :- "+dataList.get(position).getPrice());
      holder.tvNotes.setText("Notes :- "+dataList.get(position).getNotes());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView tvCreatedAt , tvStatus, tvPrice, tvNotes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreatedAt  = itemView.findViewById(R.id.tv_created_id_quotation);
            tvStatus  = itemView.findViewById(R.id.tv_status_quotation);
            tvPrice  = itemView.findViewById(R.id.tv_price_quotation);
            tvNotes  = itemView.findViewById(R.id.tv_notes_quotation);
        }
    }
}
