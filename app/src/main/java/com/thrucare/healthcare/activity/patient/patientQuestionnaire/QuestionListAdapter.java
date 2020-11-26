package com.thrucare.healthcare.activity.patient.patientQuestionnaire;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;
import com.thrucare.healthcare.pojo.QuestionInternalModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<QuestionInternalModel> listQuestion;
    ArrayList<QuestionInternalModel> listCopyList = new ArrayList<>();
    List<ModelQuestionnaire.Question> listMain;
    QuestionActivity activity;
    static int YES_NO_VALUE = 1;
    static int GROUP_VALUE = 2;
    static int TEXT_VALUE = 3;
    static int SUB_QUESTION_TEXT = 4;
    static int SUB_QUESTION_YES_NO = 4;
    Map<String , String > map  = new HashMap<>();

    private SetOnClickOnGroupQuestion setOnClickOnGroupQuestion;
    private LinearLayoutManager layoutManagerSub;
    private RecyclerView recyclerViewSubQuestion;
    private QuestionSubListAdapter mSubListAdapter;
    private int addPosition = 0;
    private int questionAdded = 0;
    private int previousAddPosition = 0;
    private int previousAddQuestionSize = 0;

    static QuestionListAdapter adapter;
    private int addPositionCopy = 0;
    private int questionAddedCopy = 0;
    private int addPosition1 = 0;
    private int questionAdded1 = 0;
    private int previousAddPosition1 = 0;
    private int previousAddQuestionSize1 = 0;

    public QuestionListAdapter(List<ModelQuestionnaire.Question> questionListMain, ArrayList<QuestionInternalModel> listQuestion,
                               QuestionActivity questionActivity) {
        this.listMain = questionListMain;
        this.listQuestion = listQuestion;
        this.activity = questionActivity;
        this.setOnClickOnGroupQuestion = (SetOnClickOnGroupQuestion) questionActivity;
        adapter = this;
        for (int i = 0; i < listQuestion.size(); i++) {
            if(!listQuestion.get(i).getType().equalsIgnoreCase("group")){
                map.put(listQuestion.get(i).getCode() , listQuestion.get(i).getAnswer());
            }
        }


        Log.d("Map" , map.toString());
    }

    public static QuestionListAdapter getInstance() {
        return adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (listQuestion.get(position).getType().equalsIgnoreCase("yesno")) {
            return YES_NO_VALUE;
        } else if (listQuestion.get(position).getType().equalsIgnoreCase("group")) {
            return GROUP_VALUE;
        } else if (listQuestion.get(position).getType().equalsIgnoreCase("SubQuestion_Text")) {
            return SUB_QUESTION_TEXT;
        } else if (listQuestion.get(position).getType().equalsIgnoreCase("SubQuestion_YesNo")) {
            return SUB_QUESTION_YES_NO;
        } else {
            return TEXT_VALUE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("ListCopy", listCopyList.toString());
        if (viewType == YES_NO_VALUE) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_item_yes_no, parent, false);
            return new QuestionListAdapter.MyYesNoViewAHolder(itemView);
        } else if (viewType == GROUP_VALUE) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_group_type, parent, false);
            return new QuestionListAdapter.MyGroupViewAHolder(itemView);
        } else if (viewType == SUB_QUESTION_TEXT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_sub_layout_text, parent, false);
            return new QuestionListAdapter.MyTextViewSubQuestionAHolder(itemView);
        } else if (viewType == SUB_QUESTION_YES_NO) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_sub_yes_no, parent, false);
            return new QuestionListAdapter.MyYesNoSubQuestionViewAHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_text_type, parent, false);
            return new QuestionListAdapter.MyTextViewAHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == YES_NO_VALUE) {
            MyYesNoViewAHolder myYesNoViewAHolder = (MyYesNoViewAHolder) holder;
            myYesNoViewAHolder.tvQuestionName.setText(listQuestion.get(position).getDisplay());
            myYesNoViewAHolder.tvQuestionNo.setText(listQuestion.get(position).getQuestionNumber() + ".");
            if (listQuestion.get(position).getAnswer().equalsIgnoreCase("yes")) {
                myYesNoViewAHolder.rdYesBtn.setChecked(true);
            } else if (listQuestion.get(position).getAnswer().equalsIgnoreCase("no")) {
                myYesNoViewAHolder.rdNoBtn.setChecked(true);
            }
        } else if (getItemViewType(position) == GROUP_VALUE) {
            MyGroupViewAHolder myYesNoViewAHolder = (MyGroupViewAHolder) holder;
            myYesNoViewAHolder.tvQuestionName.setText(listQuestion.get(position).getDisplay());
            myYesNoViewAHolder.tvQuestionNo.setText(listQuestion.get(position).getQuestionNumber() + ".");
        } else if (getItemViewType(position) == SUB_QUESTION_TEXT) {
            MyTextViewSubQuestionAHolder myTextSubViewAHolder = (MyTextViewSubQuestionAHolder) holder;
            myTextSubViewAHolder.tvQuestionName.setText(listQuestion.get(position).getDisplay());
            myTextSubViewAHolder.tvQuestionNo.setText(listQuestion.get(position).getQuestionNumber() + ".");
            String code   = listQuestion.get(position).getCode();
            String valueMap   = map.get(code);
            if(valueMap!=null){
                if(!valueMap.isEmpty()&&valueMap!=null){
                    myTextSubViewAHolder.edtText.setText(valueMap);
                }else {
                    myTextSubViewAHolder.edtText.setText(listQuestion.get(position).getAnswer());
                }
            }else {
                myTextSubViewAHolder.edtText.setText(listQuestion.get(position).getAnswer());
            }
        } else if (getItemViewType(position) == SUB_QUESTION_YES_NO) {
            MyYesNoSubQuestionViewAHolder myYesNoSubViewAHolder = (MyYesNoSubQuestionViewAHolder) holder;
            myYesNoSubViewAHolder.tvQuestionName.setText(listQuestion.get(position).getDisplay());
            myYesNoSubViewAHolder.tvQuestionNo.setText(listQuestion.get(position).getQuestionNumber() + ".");
        } else {
            MyTextViewAHolder myTextViewAHolder = (MyTextViewAHolder) holder;
            myTextViewAHolder.tvQuestionName.setText(listQuestion.get(position).getDisplay());
            myTextViewAHolder.tvQuestionNo.setText(listQuestion.get(position).getQuestionNumber() + ".");
            String code   = listQuestion.get(position).getCode();
            String valueMap   =map.get(code);
            if(valueMap!=null){
                if(!valueMap.isEmpty()&&valueMap!=null){
                    myTextViewAHolder.edtText.setText(valueMap);
                }else {
                    myTextViewAHolder.edtText.setText(listQuestion.get(position).getAnswer());
                }
            }else {
                myTextViewAHolder.edtText.setText(listQuestion.get(position).getAnswer());
            }
        }

    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }


    public class MyYesNoViewAHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        RadioButton rdYesBtn, rdNoBtn;

        public MyYesNoViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName = itemView.findViewById(R.id.tv_question_name);
            tvQuestionNo = itemView.findViewById(R.id.tv_question_number);
            rdYesBtn = itemView.findViewById(R.id.btn_rd_yes);
            rdNoBtn = itemView.findViewById(R.id.btn_rd_no);
            rdNoBtn.setOnClickListener(this);
            rdYesBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_rd_yes:
                    listQuestion.get(getAdapterPosition()).setAnswer("yes");
                    if(map.containsKey(listQuestion.get(getAdapterPosition()).getCode())){
                        map.remove(listQuestion.get(getAdapterPosition()).getCode());
                        map.put(listQuestion.get(getAdapterPosition()).getCode() , "Yes");
                    }
                    notifyDataSetChanged();
                    break;

                case R.id.btn_rd_no:
                    listQuestion.get(getAdapterPosition()).setAnswer("no");
                    if(map.containsKey(listQuestion.get(getAdapterPosition()).getCode())){
                        map.remove(listQuestion.get(getAdapterPosition()).getCode());
                        map.put(listQuestion.get(getAdapterPosition()).getCode() , "No");
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }


    public class MyYesNoSubQuestionViewAHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo;
        TextView tvQuestionName;

        public MyYesNoSubQuestionViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName = itemView.findViewById(R.id.tv_question_name);
            tvQuestionNo = itemView.findViewById(R.id.tv_question_number);
        }
    }

    public class MyGroupViewAHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        CardView cdGroupLayout;

        public MyGroupViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName = itemView.findViewById(R.id.tv_question_name_group);
            tvQuestionNo = itemView.findViewById(R.id.tv_question_number_group);
            cdGroupLayout = itemView.findViewById(R.id.card_group_card);
            cdGroupLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card_group_card:
                    if (addPosition == 0) {
                         addDataInList(getAdapterPosition());
                        // addDataInCopyList(getAdapterPosition());

                    } else {
                        int loopSize = previousAddPosition + previousAddQuestionSize - 1;
                        for (int i = previousAddPosition; i <= loopSize; i++) {
                            listQuestion.remove(previousAddPosition);
                        }
                        addPosition = 0;
                        previousAddQuestionSize = 0;
                        notifyDataSetChanged();
                    }
                    break;
            }
        }
    }
    private void addDataInList(int adapterPosition1) {
        List<ModelQuestionnaire.Question_> itemListSubQuestion = listMain.get(adapterPosition1).getQuestion();
        int adapterPosition = adapterPosition1;
        addPosition = adapterPosition + 1;
        questionAdded = itemListSubQuestion.size();
        for (int i = 0; i < itemListSubQuestion.size(); i++) {
            String value = itemListSubQuestion.get(i).getType();

            String layoutType = "";
            if (value.equalsIgnoreCase("text")) {
                layoutType = "SubQuestion_Text";
            } else {
                layoutType = "SubQuestion_YesNo";
            }
            listQuestion.add(addPosition++, new QuestionInternalModel(itemListSubQuestion.get(i).getCode(),
                    itemListSubQuestion.get(i).getDisplay(),
                    layoutType, itemListSubQuestion.get(i).getValue(), String.valueOf(i + 1)));

            String code = itemListSubQuestion.get(i).getCode();
            String value1  = "";
            if(map.containsKey(code)){
                 value1 = map.get(code);
            }
            if(!value1.isEmpty()){
                map.put(itemListSubQuestion.get(i).getCode() ,value1 );
            }else {
                map.put(itemListSubQuestion.get(i).getCode() ,itemListSubQuestion.get(i).getValue() );
            }


            Log.d("map" , map.toString());

        }
        previousAddPosition = adapterPosition + 1;
        previousAddQuestionSize = itemListSubQuestion.size();
        notifyDataSetChanged();
    }

    public class MyTextViewAHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        EditText edtText;

        public MyTextViewAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName = itemView.findViewById(R.id.tv_question_name_text);
            tvQuestionNo = itemView.findViewById(R.id.tv_question_number_text);
            edtText = itemView.findViewById(R.id.edit_text_main_question);
            edtText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listQuestion.get(getAdapterPosition()).setAnswer(s.toString());
                    if(map.containsKey(listQuestion.get(getAdapterPosition()).getCode())){
                        map.remove(listQuestion.get(getAdapterPosition()).getCode());
                        map.put(listQuestion.get(getAdapterPosition()).getCode() , s.toString() );
                    }
                    Log.d("map" , map.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }


            });
        }
    }


    public class MyTextViewSubQuestionAHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo;
        TextView tvQuestionName;
        EditText edtText;

        public MyTextViewSubQuestionAHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionName = itemView.findViewById(R.id.tv_question_name_text);
            tvQuestionNo = itemView.findViewById(R.id.tv_question_number_text);
            edtText = itemView.findViewById(R.id.edit_sub_text_name);
            edtText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listQuestion.get(getAdapterPosition()).setAnswer(s.toString());
                    if(map.containsKey(listQuestion.get(getAdapterPosition()).getCode())){
                        map.remove(listQuestion.get(getAdapterPosition()).getCode());
                        map.put(listQuestion.get(getAdapterPosition()).getCode() , s.toString() );
                    }
                    Log.d("map" , map.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                   
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }


            });
        }
    }

    interface SetOnClickOnGroupQuestion {
        void getPositionOfGroup(int position);

    }
}
