package com.thrucare.healthcare.activity.patient.order;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.thrucare.healthcare.activity.patient.home.HomeOrganizationAdapter;
import com.thrucare.healthcare.activity.provider.orders.AddLabTestActivity;
import com.thrucare.healthcare.activity.provider.quotation.QuotationListActivity;
import com.thrucare.healthcare.pojo.PatientOrderType;
import com.thrucare.healthcare.pojo.PatientOrderTypeForOthers;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.List;

public class OrderSubModuleAdapter extends RecyclerView.Adapter<OrderSubModuleAdapter.MyViewHolder> {

    Activity activity;
    List<PatientOrderType.Item> listMedication;
    String orderType;
    String forProvider ;
    Context ctx;

    List<PatientOrderTypeForOthers.Item> listOthers;
    public OrderSubModuleAdapter(List<PatientOrderType.Item> list,
                                 OrdersSubModuleActivity ordersSubModuleActivity, String orderType) {
        this.activity  = ordersSubModuleActivity;
        this.listMedication   = list;
        this.orderType  = orderType;
        this.ctx  = ordersSubModuleActivity;

    }

    public OrderSubModuleAdapter(List<PatientOrderTypeForOthers.Item> list, OrdersSubModuleActivity
            ordersSubModuleActivity, String orderType ,  String forProvider) {
        this.listOthers  = list;
        this.orderType = orderType;
        this.activity  = ordersSubModuleActivity;
        this.forProvider = forProvider;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_type_patient_item, parent, false);

        return new OrderSubModuleAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(orderType.equalsIgnoreCase(ConstantsUtils.MEDICATION_ORDER)){
            holder.tvProviderName.setText(listMedication.get(position).getProvider().getFirstName() +" "+
                    listMedication.get(position).getProvider().getMiddleName() +
                    " "+listMedication.get(position).getProvider().getLastName() );
            holder.tvHeadingLineTwo.setText("Medication:-");
            if(listMedication.get(position).getMedication().getDisplay().length()>25){
                holder.tvLineTwoValue.setText(listMedication.get(position).getMedication().getDisplay().substring(0 , 25)+"...");
            }else {
                holder.tvLineTwoValue.setText(listMedication.get(position).getMedication().getDisplay());
            }

            holder.tvDateValue.setText(listMedication.get(position).getCreatedDate().substring(0 , 10));
        }else {
            holder.tvProviderName.setText(listOthers.get(position).getProvider().getFirstName() +" "+
                    listOthers.get(position).getProvider().getMiddleName() +
                    " "+listOthers.get(position).getProvider().getLastName() );
            holder.tvHeadingLineTwo.setText("Procedure:-");
            if(listOthers.get(position).getProcedure().getDisplay().length()>25){
                holder.tvLineTwoValue.setText(listOthers.get(position).getProcedure().getDisplay().substring(0 , 25)+"...");
            }else {
                holder.tvLineTwoValue.setText(listOthers.get(position).getProcedure().getDisplay());
            }

            holder.tvDateValue.setText(listOthers.get(position).getCreatedDate().substring(0 , 10));
        }

    }

    @Override
    public int getItemCount() {
        if(orderType.equalsIgnoreCase(ConstantsUtils.MEDICATION_ORDER)){
            return listMedication.size();
        }else {
            return listOthers.size();
        }

    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvProviderName  , tvHeadingLineTwo  ,tvLineTwoValue , tvDateValue;
        LinearLayout llnLayoutSubModule , llnQuotation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProviderName  = itemView.findViewById(R.id.tv_provider_order_type);
            tvHeadingLineTwo  = itemView.findViewById(R.id.tv_line_two_heading);
            tvLineTwoValue  = itemView.findViewById(R.id.tv_line_two_value);
            tvDateValue  = itemView.findViewById(R.id.tv_date_order_type);
            llnLayoutSubModule  = itemView.findViewById(R.id.lln_layout_for_submodule);
            llnQuotation  = itemView.findViewById(R.id.lln_quotation_link);
            llnQuotation.setOnClickListener(this);
            llnLayoutSubModule.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lln_layout_for_submodule:
                    if(orderType.equalsIgnoreCase(ConstantsUtils.MEDICATION_ORDER)){

                    }else {
                        int positionAdapter  = getAdapterPosition();
                        if(forProvider.equalsIgnoreCase(ConstantsUtils.For_Provider)){
                            PatientOrderTypeForOthers.Item  itmObj  = listOthers.get(positionAdapter);
                            PreferenceUtils.insertData(activity, "PerformerName" , itmObj.getProvider().getFirstName() +" "+
                                    itmObj.getProvider().getMiddleName() +
                                    " "+itmObj.getProvider().getLastName()) ;
                            PreferenceUtils.insertData(activity, "PerformerType" , "") ;
                            PreferenceUtils.insertData(activity, "Procedure" , itmObj.getProcedure().getDisplay()) ;
                            PreferenceUtils.insertData(activity, "Status" , itmObj.getStatus().getCode());
                            PreferenceUtils.insertData(activity, "Status" , itmObj.getStatus().getCode());
                            PreferenceUtils.insertData(activity, "Date" , itmObj.getCreatedDate().substring(0 , 10));
                            activity.startActivity(new Intent(activity, AddLabTestActivity.class)
                                    .putExtra(ConstantsUtils.ORDER_TYPE , orderType));
                        }

                    }


                    break;

                case R.id.lln_quotation_link:
                     ctx.startActivity(new Intent(ctx, QuotationListActivity.class));

                    break;
            }
        }
    }
}
