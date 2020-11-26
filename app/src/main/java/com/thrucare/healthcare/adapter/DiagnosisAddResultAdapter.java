package com.thrucare.healthcare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.activity.provider.DiagnosisResultSearchActivity;
import com.thrucare.healthcare.activity.provider.MedicationProviderActivity;
import com.thrucare.healthcare.activity.provider.orders.AddMedicationActivity;
import com.thrucare.healthcare.pojo.ResultSearchProblemModel;

import java.util.List;

public class DiagnosisAddResultAdapter extends RecyclerView.Adapter {

    List<ResultSearchProblemModel> list;
    DiagnosisResultSearchActivity ctx;
    SetOnClickResult setOnClickResult;
    String lastActivity;
    SetOnClickResultStrength setOnClickResultStrength;
    public DiagnosisAddResultAdapter(List<ResultSearchProblemModel> dataList,
                                     DiagnosisResultSearchActivity diagnosisResultSearchActivity,
                                     String lastActivity) {
     this.list  = dataList;
     this.ctx  = diagnosisResultSearchActivity;
        this.lastActivity  = lastActivity;
        this.setOnClickResultStrength  =(SetOnClickResultStrength) diagnosisResultSearchActivity;
     if(lastActivity.equalsIgnoreCase("DIAGNOSIS")){
         setOnClickResult  = (SetOnClickResult) AddDiagnosisActivity.getInstance();
     }else if(lastActivity.equalsIgnoreCase("MEDICATION")){
         setOnClickResult  = (SetOnClickResult) MedicationProviderActivity.getInstance();
     }else {
         setOnClickResult  = (SetOnClickResult) AddMedicationActivity.getInstance();
     }

    }


    @Override
    public int getItemViewType(int position) {
        if(lastActivity.equalsIgnoreCase("DIAGNOSIS")){
            return 1;
            // 1 is for coming from add diagnosis part
        }else {
            return  0;
            // 0 is for coming from add medication part
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==1){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.diagonis_result_item, parent, false);

            return new DiagnosisAddResultAdapter.MyViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.medication_result_item, parent, false);

            return new DiagnosisAddResultAdapter.MyViewHolderMedication(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(lastActivity.equalsIgnoreCase("DIAGNOSIS")){
            MyViewHolder mHolder = (MyViewHolder) holder;
            mHolder.tvCodeName.setText(list.get(position).getCode());
            mHolder.tvDescription.setText(list.get(position).getDescription());
        }else {
            MyViewHolderMedication mHolder = (MyViewHolderMedication) holder;
            mHolder.tvDescription.setText(list.get(position).getCode());
        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCodeName, tvDescription ;
        LinearLayout llnCardResult;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           tvCodeName  = itemView.findViewById(R.id.tv_code_diagnosis_result);
           tvDescription  = itemView.findViewById(R.id.tv_descrption_diagnosis_result);
           llnCardResult  = itemView.findViewById(R.id.lln_card_search_result);
           llnCardResult.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.lln_card_search_result:
                    int position  = getAdapterPosition();
                    ResultSearchProblemModel Obj  = list.get(position);
                    setOnClickResult.getDataOfSearchResult(Obj);
                    ctx.onBackPressed();
                    break;

            }
        }
    }

    public class MyViewHolderMedication extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView  tvDescription ;
        LinearLayout llnCardResult;



        public MyViewHolderMedication(@NonNull View itemView) {
            super(itemView);
            tvDescription  = itemView.findViewById(R.id.tv_descrption_medication_result);
            llnCardResult  = itemView.findViewById(R.id.lln_card_search_result_medication);
            llnCardResult.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.lln_card_search_result_medication:
                    int position  = getAdapterPosition();
                    ResultSearchProblemModel Obj  = list.get(position);
                    //setOnClickResult.getDataOfSearchResult(Obj);
                   // ctx.onBackPressed();

                    setOnClickResultStrength.SetOnClickResultStrength(Obj , position);
                    ctx.binding.editSearchMedication.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }
   public interface SetOnClickResult{
        void getDataOfSearchResult(ResultSearchProblemModel Obj);
    }

    public interface SetOnClickResultStrength{
        void SetOnClickResultStrength( ResultSearchProblemModel Obj , int position);
    }
}
