package com.thrucare.healthcare.activity.patient.patientQuestionnaire;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.MyViewHolderQuestionnaire> {

    List<ModelQuestionnaire.Item> itemsList ;
    static  QuestionnaireActivity activity;
    public QuestionnaireAdapter(List<ModelQuestionnaire.Item> itemList, QuestionnaireActivity questionnaireActivity) {
        this.activity  = questionnaireActivity;
        this.itemsList  = itemList;
    }

    @NonNull
    @Override
    public MyViewHolderQuestionnaire onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionnaire_item, parent, false);
        return new QuestionnaireAdapter.MyViewHolderQuestionnaire(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderQuestionnaire holder, int position) {
        holder.tvQuestionnaire.setText(itemsList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class MyViewHolderQuestionnaire extends RecyclerView.ViewHolder {

        TextView tvQuestionnaire ;
        ImageView imgPlusQuestionnaire;

        public MyViewHolderQuestionnaire(@NonNull View itemView) {
            super(itemView);
            tvQuestionnaire  = itemView.findViewById(R.id.tv_questionnaire_title);
            imgPlusQuestionnaire  = itemView.findViewById(R.id.img_questionnaire_plus);
            imgPlusQuestionnaire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition  = getAdapterPosition();
                    activity.startActivity(new Intent(activity, QuestionActivity.class )
                            .putExtra(ConstantsUtils.QUESTION_POSITION_VALUE , adapterPosition));


                }
            });

        }
    }
}
