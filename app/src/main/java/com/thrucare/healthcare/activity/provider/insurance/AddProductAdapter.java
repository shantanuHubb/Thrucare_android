package com.thrucare.healthcare.activity.provider.insurance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ProductInsuranceModel;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.modelClasses.ProviderInsuranceList;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.MyViewHolder> {
      List<ProductInsuranceModel> list = new ArrayList<>();
      Context ctx ;
     public  List<String> listSpinner ;
    List<ModelCodeAndDisplay> listCodeAndDisPlay ;
    List<ProviderInsuranceList.Product> ProductList ;
    SetOnClickProductListener setOnClickProductListener;
    public AddProductAdapter(List<ProductInsuranceModel> list,
                             AddNewInsuranceActivity addNewInsuranceActivity, List<String> listSpinner, List<ModelCodeAndDisplay> optionsListInsuranceModel) {
        this.ctx  = addNewInsuranceActivity;
        this.list  = list;
        this.listSpinner  = listSpinner;
        this.listCodeAndDisPlay  = optionsListInsuranceModel;
        this.setOnClickProductListener   = (SetOnClickProductListener) addNewInsuranceActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_insurance_item, parent, false);

        return new AddProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.get(position).getIsAdd()==1){
            holder.imgPlus.setImageDrawable(ctx.getDrawable(R.drawable.plus));
        }else {
            holder.imgPlus.setImageDrawable(ctx.getDrawable(R.drawable.minus));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.simple_item_spinner, listSpinner);
        holder.spinnerProduct.setAdapter(adapter); // this will set list of values to spinner
        holder.spinnerProduct.setSelection(listSpinner.indexOf(listSpinner.get(0)));

        if(!list.get(position).getSelectedCode().isEmpty()){
            holder.spinnerProduct.setSelection(list.get(position).getSelectedPosition());

        }else {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ctx, R.layout.simple_item_spinner, listSpinner);
            holder.spinnerProduct.setAdapter(adapter1); // this will set list of values to spinner
            holder.spinnerProduct.setSelection(listSpinner.indexOf(listSpinner.get(0)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         Spinner spinnerProduct ;
         ImageView imgPlus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spinnerProduct  = itemView.findViewById(R.id.spinner_product);
            imgPlus  = itemView.findViewById(R.id.img_plus_product);
            imgPlus.setOnClickListener(this);
            //spinnerProduct.setOnClickListener(this);
            spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {
                   int adapterPosition =  getAdapterPosition();
                   list.get(adapterPosition).setSelectedCode(listCodeAndDisPlay.get(selectedPosition).getCode());
                   list.get(adapterPosition).setSelectedDisplay(listCodeAndDisPlay.get(selectedPosition).getDisplayName());
                   list.get(adapterPosition).setSelectedPosition(selectedPosition);
                   setOnClickProductListener.getListOfProduct(list);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_plus_product:
                    int addOrPlus  = list.get( getAdapterPosition()).getIsAdd();
                    if(addOrPlus==1){
                        list.add(new ProductInsuranceModel(1,  ""  , 0 , ""));
                        list.get(getAdapterPosition()).setIsAdd(0);
                    }else {
                        list.remove(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }


        public  interface SetOnClickProductListener{
          void getListOfProduct ( List<ProductInsuranceModel> list);
        }
}
