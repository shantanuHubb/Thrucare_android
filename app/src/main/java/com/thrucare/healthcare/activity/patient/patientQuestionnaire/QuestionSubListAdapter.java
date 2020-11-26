package com.thrucare.healthcare.activity.patient.patientQuestionnaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;

import java.util.List;

public class QuestionSubListAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ModelQuestionnaire.Question_> listQuestion;
    QuestionActivity activity;
    static int YES_NO_VALUE = 1;
    static int TEXT_VALUE = 3;

    public QuestionSubListAdapter(List<ModelQuestionnaire.Question_> listQuestion, QuestionActivity questionActivity) {
        this.activity  = questionActivity;
        this.listQuestion  = listQuestion;
    }

    @Override
    public int getItemViewType(int position) {
        if(listQuestion.get(position).getType().equalsIgnoreCase("yesno")){
            return YES_NO_VALUE;
        }else {
            return TEXT_VALUE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==YES_NO_VALUE){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_item_yes_no, parent, false);
            return new QuestionSubListAdapter.MyYesNoViewAHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_text_type, parent, false);
            return new QuestionSubListAdapter.MyTextViewAHolder(itemView);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==YES_NO_VALUE){
            QuestionSubListAdapter.MyYesNoViewAHolder myYesNoViewAHolder  = (QuestionSubListAdapter.MyYesNoViewAHolder) holder;
            ((QuestionSubListAdapter.MyYesNoViewAHolder) holder).tvQuestionName.setText(listQuestion.get(position).getDisplay());
            ((QuestionSubListAdapter.MyYesNoViewAHolder) holder).tvQuestionNo.setText(position+1+".");
        }else {
            QuestionSubListAdapter.MyTextViewAHolder myYesNoViewAHolder  = (QuestionSubListAdapter.MyTextViewAHolder) holder;
            ((QuestionSubListAdapter.MyTextViewAHolder) holder).tvQuestionName.setText(listQuestion.get(position).getDisplay());
            ((QuestionSubListAdapter.MyTextViewAHolder) holder).tvQuestionNo.setText(position+1+".");
        }

    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }


    public class MyYesNoViewAHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        public MyYesNoViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName  = itemView.findViewById(R.id.tv_question_name);
            tvQuestionNo  = itemView.findViewById(R.id.tv_question_number);
        }
    }



    public class MyTextViewAHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        public MyTextViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName  = itemView.findViewById(R.id.tv_question_name_text);
            tvQuestionNo  = itemView.findViewById(R.id.tv_question_number_text);
        }
    }
}
