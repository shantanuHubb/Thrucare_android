package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.databinding.ActivityAddFamilyHistoryBinding;
import com.thrucare.healthcare.pojo.FamilyHistory;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFamilyHistoryActivity extends AppCompatActivity {

    private ApiInterface mApiService;
    ActivityAddFamilyHistoryBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFamilyHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mApiService = ApiUtils.getApiService();
        binding.tvSaveConFamilyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFamilyHistoryApiCalling();
                AddFamilyHistoryActivity.this.onBackPressed();
            }
        });

        binding.tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llnAddMoreLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addFamilyHistoryApiCalling() {

        JsonObject jsonObject  = new JsonObject();
        JsonArray jsonArray  = new JsonArray();
        JsonObject jsonObject1  = new JsonObject();
        jsonObject1.addProperty("problem", "suger");
        jsonObject1.addProperty("onset_age", "60");
        jsonArray.add(jsonObject1);
        jsonObject.addProperty("relation", "FTH");
        jsonObject.add("conditions", jsonArray);

        Call<FamilyHistory.PatientFamilyHistory> call  = mApiService.addFamilyHistory(jsonObject);
        call.enqueue(new Callback<FamilyHistory.PatientFamilyHistory>() {
            @Override
            public void onResponse(Call<FamilyHistory.PatientFamilyHistory> call, Response<FamilyHistory.PatientFamilyHistory> response) {

                String code  = String.valueOf(response.code());

                if(response.code()==201){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddFamilyHistoryActivity.this, "Added Family History", Toast.LENGTH_LONG).show();
                            AddFamilyHistoryActivity.super.onBackPressed();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FamilyHistory.PatientFamilyHistory> call, Throwable t) {
                t.printStackTrace();
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