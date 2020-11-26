package com.thrucare.healthcare.activity.provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAllergiesSearchBinding;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.QuestionInternalModel;
import com.thrucare.healthcare.pojo.ReportCategory;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllergiesSearchActivity extends AppCompatActivity  {
    private List<String> optionsListReactions= new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListReactionModel = new ArrayList<>();
    private ActivityAllergiesSearchBinding binding;
    private ApiInterface mApisService;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllergiesSearchAdpater mProductAdapter;
    private static AllergiesSearchActivity activity;
    private String searchForValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllergiesSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        activity = this;

        try {
            searchForValue  = getIntent().getStringExtra("SEARCH_FOR");
        }
        catch (Exception e){

        }

        mApisService  = ApiUtils.getApiService();
        getSupportActionBar().hide();
        setContentView(view);
        recyclerView  =  view.findViewById(R.id.recyclerViewAllergiesValue);
        layoutManager = new LinearLayoutManager(this);
        setListeners();
    }

    public static AllergiesSearchActivity getInstance(){
        return activity;
    }
    private void setListeners() {
        binding.editSearchAllergiesBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try{
                    if(s.length()>=3){
                        if(searchForValue.equalsIgnoreCase("SUBSTANCE")){
                            getDataSubstanceApiCalling(s.toString() , searchForValue);
                        }else if(searchForValue.equalsIgnoreCase("REACTION")){
                            getDataReactionApiCalling(s.toString() , searchForValue);
                        }else if(searchForValue.equalsIgnoreCase("MEDICATION")){
                            getDataMedicationReactionApiCalling(s.toString(), searchForValue);
                        }else if(searchForValue.equalsIgnoreCase("MEDICATION_ADD")) {
                            getDataMedicationReactionApiCalling(s.toString(), searchForValue);
                        }else if(searchForValue.equalsIgnoreCase("ADD_LAB")||
                                searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_REASONS_DEVICE_ORDER)){
                            getDataMedicationReactionApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase("ADD_PERFORMER_TYPE")){
                            getDataMedicationPerformerApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase(ConstantsUtils.ADD_TYPE_DEVICE_ORDER)){
                            getDeviceTypeOrderApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase("ADD_PROCEDURE")){
                            getDataMedicationProcedureApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase("ADD_VACCINE")){
                            getVaccineForImmunizationApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_PROCEDURE")){
                            getDataMedicationProcedureApiCalling(s.toString(), searchForValue);
                        }
                        else if(searchForValue.equalsIgnoreCase("ADD_MEDICAL_CONCLUSION")){
                            getDataMedicationConclusionApiCalling(s.toString(), searchForValue);
                        }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void getVaccineForImmunizationApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=vaccine-code&terms="+ toString)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if(response.isSuccessful()){
                    String resStr  = response.body().string();
                    if(resStr!=null){
                        JSONObject jsonObject  = null;
                        try {
                            jsonObject  = new JSONObject(resStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonObject!=null){
                            JSONArray arrayObj = null;
                            try {
                                arrayObj  = jsonObject.getJSONArray("items");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(arrayObj.length()>0&&arrayObj!=null){
                                for(int i =0 ; i<arrayObj.length() ; i++){
                                    try {
                                        JSONObject  jsonObject1=   arrayObj.getJSONObject(i);
                                        optionsListReactionModel.add( new ModelCodeAndDisplay(jsonObject1.getString("code") ,
                                                jsonObject1.getString("display")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                }


                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });
    }

    private void getDeviceTypeOrderApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=device-type&terms="+ toString)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if(response.isSuccessful()){
                    String resStr  = response.body().string();
                    if(resStr!=null){
                        JSONObject jsonObject  = null;
                        try {
                            jsonObject  = new JSONObject(resStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonObject!=null){
                            JSONArray arrayObj = null;
                            try {
                                arrayObj  = jsonObject.getJSONArray("items");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(arrayObj.length()>0&&arrayObj!=null){
                                for(int i =0 ; i<arrayObj.length() ; i++){
                                    try {
                                        JSONObject  jsonObject1=   arrayObj.getJSONObject(i);
                                        optionsListReactionModel.add( new ModelCodeAndDisplay(jsonObject1.getString("code") ,
                                                jsonObject1.getString("display")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                }


                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });
    }

    private void getDataMedicationConclusionApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type="+ toString)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if(response.isSuccessful()){
                    String resStr  = response.body().string();
                    if(resStr!=null){
                        JSONObject jsonObject  = null;
                        try {
                             jsonObject  = new JSONObject(resStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonObject!=null){
                            JSONArray arrayObj = null;
                            try {
                                 arrayObj  = jsonObject.getJSONArray("items");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(arrayObj.length()>0&&arrayObj!=null){
                                for(int i =0 ; i<arrayObj.length() ; i++){
                                    try {
                                       JSONObject  jsonObject1=   arrayObj.getJSONObject(i);
                                        optionsListReactionModel.add( new ModelCodeAndDisplay(jsonObject1.getString("code") ,
                                                jsonObject1.getString("display")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                }


                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });

    }

  /*  private void getDataMedicationConclusionApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();
        Call<ReportCategory.ResponseReportCategory> call  = mApisService.getConclusion(toString);

        call.enqueue(new Callback<ReportCategory.ResponseReportCategory>() {
            @Override
            public void onResponse(Call<ReportCategory.ResponseReportCategory> call,
                                   Response<ReportCategory.ResponseReportCategory> response) {
                ReportCategory.ResponseReportCategory reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListReactionModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }

                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);
            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {

            }
        });
    }
*/
    private void getDataMedicationProcedureApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://clinicaltables.nlm.nih.gov/api/loinc_items/v3/search?df=text,LOINC_NUM&terms="+ toString)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {




                if(response.isSuccessful()){
                    String resArrayObj  = response.body().string();
                    JSONArray jsonArray  = null;
                    try {
                         jsonArray  = new JSONArray(resArrayObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(jsonArray!=null){
                        JSONArray jsonArray1  = null;
                        try {
                             jsonArray1 = jsonArray.getJSONArray(3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonArray1!=null){
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                try {
                                    optionsListReactionModel.add(new ModelCodeAndDisplay(String.valueOf(jsonArray1.getJSONArray(i).get(1)),
                                            String.valueOf(jsonArray1.getJSONArray(i).get(0))));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }


                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });

    }

    private void getDataMedicationPerformerApiCalling(String toString, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=participant-role&terms="+ toString)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseString  = null;
                JSONObject jsonObjectResponse = null;
                try{
                    responseString   = response.body().string();
                }catch (Exception e){

                }
                if(responseString!=null){
                    try {
                        jsonObjectResponse = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArrayItems = null;
                    try {
                        jsonArrayItems  = jsonObjectResponse.getJSONArray("items");
                        if(jsonArrayItems!=null){
                            for (int i = 0; i < jsonArrayItems.length(); i++) {
                                JSONObject obj =  jsonArrayItems.getJSONObject(i);
                                // optionsListReactions.add(jsonArrayItems.get(i).);
                                optionsListReactionModel.add(new ModelCodeAndDisplay(obj.getString("code"),
                                        obj.getString("display")));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });

    }

    private void getDataMedicationReactionApiCalling(String searchValue , String searchForValue ) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=medication-reason&terms="+ searchValue)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseString  = null;
                JSONObject jsonObjectResponse = null;
                try{
                    responseString   = response.body().string();
                }catch (Exception e){

                }
                if(responseString!=null){
                    try {
                        jsonObjectResponse = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArrayItems = null;
                    try {
                        jsonArrayItems  = jsonObjectResponse.getJSONArray("items");
                        if(jsonArrayItems!=null){
                            for (int i = 0; i < jsonArrayItems.length(); i++) {
                                JSONObject obj =  jsonArrayItems.getJSONObject(i);
                                // optionsListReactions.add(jsonArrayItems.get(i).);
                                optionsListReactionModel.add(new ModelCodeAndDisplay(obj.getString("code"),
                                        obj.getString("display")));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });

    }

    private void getDataReactionApiCalling(String searchValue, String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=allergy-reaction&terms="+ searchValue)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseString  = null;
                JSONObject jsonObjectResponse = null;
                try{
                    responseString   = response.body().string();
                }catch (Exception e){

                }
                if(responseString!=null){
                    try {
                        jsonObjectResponse = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArrayItems = null;
                    try {
                        jsonArrayItems  = jsonObjectResponse.getJSONArray("items");
                        if(jsonArrayItems!=null){
                            for (int i = 0; i < jsonArrayItems.length(); i++) {
                                JSONObject obj =  jsonArrayItems.getJSONObject(i);
                                // optionsListReactions.add(jsonArrayItems.get(i).);
                                optionsListReactionModel.add(new ModelCodeAndDisplay(obj.getString("code"),
                                        obj.getString("display")));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                if(optionsListReactionModel.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                    AllergiesSearchActivity.this , searchForValue );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mProductAdapter);
                            mProductAdapter.notifyDataSetChanged();
                        }
                    });
                }
                hideKeyboard(AllergiesSearchActivity.this);

            }
        });

    }


    private void getDataSubstanceApiCalling(String searchValue , String searchForValue) {
        optionsListReactionModel.clear();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
              //  .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=allergy-reaction&terms="+ searchValue)
                .url("https://private-a4094-thrucare.apiary-mock.com/reference/search?type=allergy-substance&terms="+ searchValue)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
              e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseString  = null;
                JSONObject jsonObjectResponse = null;
                   try{
                        responseString   = response.body().string();
                   }catch (Exception e){

                   }
                   if(responseString!=null){
                       try {
                           jsonObjectResponse = new JSONObject(responseString);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       JSONArray jsonArrayItems = null;
                       try {
                           jsonArrayItems  = jsonObjectResponse.getJSONArray("items");
                           if(jsonArrayItems!=null){
                               for (int i = 0; i < jsonArrayItems.length(); i++) {
                               JSONObject obj =  jsonArrayItems.getJSONObject(i);
                                  // optionsListReactions.add(jsonArrayItems.get(i).);
                                   optionsListReactionModel.add(new ModelCodeAndDisplay(obj.getString("code"),
                                           obj.getString("display")));
                               }
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }


                   }

                     if(optionsListReactionModel.size()>0){
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                                         AllergiesSearchActivity.this , searchForValue );
                                 recyclerView.setLayoutManager(layoutManager);
                                 recyclerView.setAdapter(mProductAdapter);
                                 mProductAdapter.notifyDataSetChanged();
                             }
                         });
                     }
                    hideKeyboard(AllergiesSearchActivity.this);

            }
        });













/*


        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getAllergiesReactions(searchValue);
        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListReactions.add(reObj.getItems().get(i).getDisplay());
                    optionsListReactionModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }

                mProductAdapter = new AllergiesSearchAdpater(optionsListReactionModel,
                        AllergiesSearchActivity.this );
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mProductAdapter);
                mProductAdapter.notifyDataSetChanged();
                hideKeyboard(AllergiesSearchActivity.this);

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}