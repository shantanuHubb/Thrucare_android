package com.thrucare.healthcare.activity.patient.patientQuestionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.DiagnosisResultSearchActivity;
import com.thrucare.healthcare.adapter.DiagnosisAddResultAdapter;
import com.thrucare.healthcare.databinding.ActivityAddDiagnosisBinding;
import com.thrucare.healthcare.databinding.ActivityQuestionnaireBinding;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireActivity extends AppCompatActivity {

    private ActivityQuestionnaireBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface mServiceApi;
    private QuestionnaireAdapter mProductAdapter;
    private static List<ModelQuestionnaire.Item> itemList;
    private static QuestionnaireActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionnaireBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        activity  = this;
        recyclerView  =  view.findViewById(R.id.recyclerview_questionnaire);
        layoutManager = new LinearLayoutManager(this);
        mServiceApi  = ApiUtils.getApiService();
        getQuestionnaireApiCalling();
    }

    public static List<ModelQuestionnaire.Question> getListOfQuestion(int position){
        List<ModelQuestionnaire.Question> listQuestion  = itemList.get(position).getQuestion();
        return listQuestion;
    }
    private void getQuestionnaireApiCalling() {
        Call<ModelQuestionnaire.ResponseQuestion> call  = mServiceApi.getQuestionnaire();
        call.enqueue(new Callback<ModelQuestionnaire.ResponseQuestion>() {
            @Override
            public void onResponse(Call<ModelQuestionnaire.ResponseQuestion> call,
                                   Response<ModelQuestionnaire.ResponseQuestion> response) {

                if(response.isSuccessful()){
                   itemList  = response.body().getItems();

                   if(itemList.size()>0&& itemList!=null){
                       mProductAdapter = new QuestionnaireAdapter(itemList,
                               QuestionnaireActivity.this);
                       recyclerView.setLayoutManager(layoutManager);
                       recyclerView.setAdapter(mProductAdapter);
                   }

                }

            }

            @Override
            public void onFailure(Call<ModelQuestionnaire.ResponseQuestion> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}