package com.thrucare.healthcare.activity.patient.patientQuestionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityQuestionBinding;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;
import com.thrucare.healthcare.pojo.QuestionInternalModel;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity implements
        QuestionListAdapter.SetOnClickOnGroupQuestion ,
        View.OnClickListener{

    private ActivityQuestionBinding binding;
    private int position;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private QuestionListAdapter mProductAdapter;
    private List<ModelQuestionnaire.Question> listQuestion;
    private RecyclerView recyclerViewSubQuestion;
    private LinearLayoutManager layoutManagerSub;
    private QuestionSubListAdapter mSubListAdapter;
    private ApiInterface mServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        mServiceApi  = ApiUtils.getApiService();
        recyclerView  =  view.findViewById(R.id.recyclerView_question_list);
        layoutManager = new LinearLayoutManager(this);
        binding.llnSubmitQuestion.setOnClickListener(this);
        try{
            position = getIntent().getIntExtra(ConstantsUtils.QUESTION_POSITION_VALUE, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        //  getting question model list from the using static method
        listQuestion  = QuestionnaireActivity.getListOfQuestion(position);
        // sending list data to the adapter

        // question List cast to internal model
         castToInternalModel(listQuestion);


    }

    private void castToInternalModel(List<ModelQuestionnaire.Question> listQuestion) {
        ArrayList<QuestionInternalModel> list  = new ArrayList<>();
        for(int i =0 ; i<listQuestion.size() ; i++){
            try {
                String valueAns  = listQuestion.get(i).getValue();
                if(valueAns==null){
                    list.add(new QuestionInternalModel(listQuestion.get(i).getCode(),
                            listQuestion.get(i).getDisplay(),
                            listQuestion.get(i).getType() , "" , String.valueOf(i+1)));
                }else {
                        list.add(new QuestionInternalModel(listQuestion.get(i).getCode(),
                                listQuestion.get(i).getDisplay(),
                                listQuestion.get(i).getType() , valueAns , String.valueOf(i+1)));

                }

            }catch (Exception e){
              e.printStackTrace();
            }


        }
        mProductAdapter = new QuestionListAdapter(listQuestion , list,
                QuestionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProductAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setCancelable(true);
                adb.setTitle(Html.fromHtml("<font color='#334D85'>Are you want to save selected questions ?</font>"));
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BaseUtils.showToast(QuestionActivity.this, "Question Submitted ");
                        QuestionActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        QuestionActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void getPositionOfGroup(int position) {
        /*recyclerViewSubQuestion  = findViewById(R.id.recyclerView_sub_question);
        List<ModelQuestionnaire.Question_> itemListSubQuestion1  = new ArrayList<>();
        mSubListAdapter = new QuestionSubListAdapter(itemListSubQuestion1, QuestionActivity.this);*/
    }

    @Override
    public void onBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder adb = new AlertDialog.Builder(QuestionActivity.this);
                adb.setCancelable(true);
                adb.setTitle(Html.fromHtml("<font color='#334D85'>Are you want to save selected questions ?</font>"));
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BaseUtils.showToast(QuestionActivity.this, "Question Submitted ");
                        QuestionActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        QuestionActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lln_submit_question:
                Map<String  , String > listQuestion  = QuestionListAdapter.getInstance().map;
                saveQuestions(listQuestion);
                super.onBackPressed();
                break;
        }
    }

    private void saveQuestions(Map<String , String > listQuestion) {
        JsonObject mainJsonObj  = new JsonObject();
        JsonArray jsonArray  = new JsonArray();

        for(Map.Entry<String,String> entry : listQuestion.entrySet()){
           JsonObject jsonObject  = new JsonObject();
           jsonObject.addProperty("code" , entry.getKey());
           jsonObject.addProperty("value" , entry.getValue());
           jsonArray.add(jsonObject);
        }

        mainJsonObj.add("question" , jsonArray);
        Call<ResponseBody> call  = mServiceApi.saveIndividualQuestion("application/json" , mainJsonObj);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    BaseUtils.showToast(QuestionActivity.this , "Question Submitted ");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
             t.printStackTrace();
            }
        });


    }
}
