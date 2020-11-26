package com.thrucare.healthcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddMedicalReportActivity;
import com.thrucare.healthcare.activity.provider.AllergiesReportProviderActivity;
import com.thrucare.healthcare.activity.provider.AllergiesSearchActivity;
import com.thrucare.healthcare.activity.provider.MedicationProviderActivity;
import com.thrucare.healthcare.activity.provider.addImnunization.AddNewImmunizationActivity;
import com.thrucare.healthcare.activity.provider.deviceOrder.AddNewDeviceOrderActivity;
import com.thrucare.healthcare.activity.provider.orders.AddLabTestActivity;
import com.thrucare.healthcare.activity.provider.orders.AddMedicationActivity;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class AllergiesSearchAdpater extends RecyclerView.Adapter<AllergiesSearchAdpater.MyViewHolderAllergiesSearch> {

    List<ModelCodeAndDisplay> dataList;
    Context ctx;
    String searchForValue ;
    SetOnClickAllergiesSearch setOnClickAllergiesSearch;
    SetOnClickPerformer setOnClickPerformer;
    SetOnClickTypeMedicalReport setOnClickTypeMedicalReport;

    public AllergiesSearchAdpater(List<ModelCodeAndDisplay> list,
                                  AllergiesSearchActivity allergiesSearchActivity,
                                   String searchValue) {
        this.dataList = list;
        this.ctx  = allergiesSearchActivity;
        this.searchForValue  = searchValue;
        if(searchForValue.equalsIgnoreCase("REACTION")||searchForValue.equalsIgnoreCase("SUBSTANCE")){
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) AllergiesReportProviderActivity.getInstance();
        }else if(searchForValue.equalsIgnoreCase("MEDICATION")) {
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) MedicationProviderActivity.getInstance();
        }
        else if(searchForValue.equalsIgnoreCase("ADD_LAB")) {
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) AddLabTestActivity.getInstance();

        }

        else if(searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_REASONS_DEVICE_ORDER)) {
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) AddNewDeviceOrderActivity.getInstance();

        }
        else if(searchForValue.equalsIgnoreCase("ADD_PERFORMER_TYPE")) {
            this.setOnClickPerformer  = (SetOnClickPerformer) AddLabTestActivity.getInstance();

        }
        else if(searchForValue.equalsIgnoreCase("ADD_VACCINE")) {
            this.setOnClickPerformer  = (SetOnClickPerformer) AddNewImmunizationActivity.getInstance();

        }
        else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_PROCEDURE")) {
            this.setOnClickTypeMedicalReport  = (SetOnClickTypeMedicalReport) AddMedicalReportActivity.getInstance();

        }
        else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_CONCLUSION")) {
            this.setOnClickTypeMedicalReport  = (SetOnClickTypeMedicalReport) AddMedicalReportActivity.getInstance();

        }
        else if(searchForValue.equalsIgnoreCase("ADD_PROCEDURE")) {
            this.setOnClickPerformer  = (SetOnClickPerformer) AddLabTestActivity.getInstance();

        }

        else if(searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_TYPE_DEVICE_ORDER)) {
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) AddNewDeviceOrderActivity.getInstance();

        }else {
            this.setOnClickAllergiesSearch  = (SetOnClickAllergiesSearch) AddMedicationActivity.getInstance();
             }




    }

    @NonNull
    @Override
    public AllergiesSearchAdpater.MyViewHolderAllergiesSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allergies_search_item, parent, false);

        return new AllergiesSearchAdpater.MyViewHolderAllergiesSearch(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAllergiesSearch holder, int position) {
       holder.tvCode.setText(dataList.get(position).getCode());
       holder.tvDisplayDes.setText(dataList.get(position).getDisplayName());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolderAllergiesSearch extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCode , tvDisplayDes;
        LinearLayout llnAllergiesCardResult;
        public MyViewHolderAllergiesSearch(@NonNull View itemView) {
            super(itemView);
            tvCode  = itemView.findViewById(R.id.tv_code_allergies_result);
            tvDisplayDes  = itemView.findViewById(R.id.tv_descrption_allergies_result);
            llnAllergiesCardResult  = itemView.findViewById(R.id.lln_card_search_allergies);
            llnAllergiesCardResult.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lln_card_search_allergies:
                    if(searchForValue.equalsIgnoreCase("SUBSTANCE")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForSubstance(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }else if(searchForValue.equalsIgnoreCase("REACTION")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }else if(searchForValue.equalsIgnoreCase("MEDICATION")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase("ADD_LAB")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase("ADD_PERFORMER_TYPE")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        String code = dataList.get(getAdapterPosition()).getCode();
                        setOnClickPerformer.onClick(displayName,code);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase("ADD_PROCEDURE")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        String code = dataList.get(getAdapterPosition()).getCode();
                        setOnClickPerformer.onClickProcedure(displayName,code);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_CONCLUSION")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickTypeMedicalReport.onClickConclusion(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_TYPE_DEVICE_ORDER)){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForSubstance(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_REASONS_DEVICE_ORDER)){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }
                    else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_PROCEDURE")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }

                    else if(searchForValue.equalsIgnoreCase("ADD_VACCINE")){
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        String code = dataList.get(getAdapterPosition()).getCode();
                        setOnClickPerformer.onClickProcedure(displayName,code);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }else {
                        String displayName = dataList.get(getAdapterPosition()).getDisplayName();
                        setOnClickAllergiesSearch.getValueForReaction(displayName);
                        AllergiesSearchActivity.getInstance().onBackPressed();
                    }

                break;
            }
        }
    }

    public interface SetOnClickAllergiesSearch{
        void getValueForSubstance(String  display);
        void getValueForReaction (String  display);
    }


    public interface SetOnClickPerformer{
        void onClick(String displayName, String displayCode);
        void onClickProcedure(String displayName, String displayCode);
    }

    public interface SetOnClickTypeMedicalReport {
        void  onClick(String  display);
        void onClickConclusion(String display);
    }
}
