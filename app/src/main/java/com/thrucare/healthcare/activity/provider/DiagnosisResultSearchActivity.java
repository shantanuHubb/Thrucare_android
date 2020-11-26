package com.thrucare.healthcare.activity.provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.orders.AddMedicationActivity;
import com.thrucare.healthcare.adapter.DiagnosisAddResultAdapter;
import com.thrucare.healthcare.databinding.ActivityDiagnosisResultSearchBinding;
import com.thrucare.healthcare.pojo.ResultSearchProblemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiagnosisResultSearchActivity extends AppCompatActivity implements
        DiagnosisAddResultAdapter.SetOnClickResultStrength {

    public ActivityDiagnosisResultSearchBinding binding;
    private List<ResultSearchProblemModel> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DiagnosisAddResultAdapter mProductAdapter;
    private String lastActivity;
    public static DiagnosisResultSearchActivity activity;
    private JSONArray jsonArray;
    private List<String> strength;
    private String valueStrength = "";
    private String valueMedication= "";
    private String valueCodeMedication = "";

    SetOnClickResult setOnClickResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        getSupportActionBar().hide();
        binding = ActivityDiagnosisResultSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        lastActivity  = getIntent().getStringExtra("LAST_ACTIVITY");

        if(lastActivity.equalsIgnoreCase("Medication")){
            setOnClickResult  = (SetOnClickResult) MedicationProviderActivity.getInstance();
        }else {
            setOnClickResult  = (SetOnClickResult) AddMedicationActivity.getInstance();
        }

        recyclerView  =  view.findViewById(R.id.recylerViewResultDiagnosis);
        layoutManager = new LinearLayoutManager(this);

        // method for edit search watcher
        checkSearch();


        binding.imgBackResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiagnosisResultSearchActivity.super.onBackPressed();
            }
        });



        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valueStrength.equalsIgnoreCase("Select")||valueStrength.isEmpty()){
                    showToast("Please select the strength");
                }else {
                    setOnClickResult.getDataOfSearchResult(valueStrength, valueCodeMedication, valueMedication);
                    DiagnosisResultSearchActivity.super.onBackPressed();
                }

            }
        });
        binding.editSearchMedication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueStrength  =  strength.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static DiagnosisResultSearchActivity getInstance(){
        return activity;
    }
    private void checkSearch() {
        binding.editSearchDiagnosis.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            try{
               if(s.length()>=3){
                   if(lastActivity.equalsIgnoreCase("DIAGNOSIS")){
                       searchDiagnosiApiCallingForResult(s.toString());
                   }else {
                       searchMedicationApiCallingForResult(s.toString());
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

    private void searchDiagnosiApiCallingForResult(String searchValue) {
        dataList.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://clinicaltables.nlm.nih.gov/api/icd10cm/v3/search?sf=code,name&df=code,name&terms="+ searchValue)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resObj  = response.body().string();
                    JSONArray jsonArray = new JSONArray(resObj);
                    JSONArray array  = jsonArray.getJSONArray(3);
                    for(int i = 0 ; i<array.length(); i++){
                        JSONArray objValue  = array.getJSONArray(i);
                        dataList.add(new ResultSearchProblemModel(objValue.getString(0), objValue.getString(1)));
                    }
                    Log.d("Resp", dataList.toString());

                    if(dataList.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.llnCodeDes.setVisibility(View.VISIBLE);
                                binding.llnKeepTrying.setVisibility(View.VISIBLE);
                                mProductAdapter = new DiagnosisAddResultAdapter(dataList,
                                        DiagnosisResultSearchActivity.this,
                                        lastActivity);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(mProductAdapter);
                                mProductAdapter.notifyDataSetChanged();
                                hideKeyboard(DiagnosisResultSearchActivity.this);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
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

    private void searchMedicationApiCallingForResult(String searchValue) {
        dataList.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://clinicaltables.nlm.nih.gov/api/rxterms/v3/search?ef=STRENGTHS_AND_FORMS,RXCUIS&terms="+searchValue)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resObj  = response.body().string();
                     jsonArray = new JSONArray(resObj);
                    JSONArray array  = jsonArray.getJSONArray(3);
                    for(int i = 0 ; i<array.length(); i++){
                        JSONArray objValue  = array.getJSONArray(i);
                        dataList.add(new ResultSearchProblemModel(objValue.getString(0), ""));
                    }
                    Log.d("Resp", dataList.toString());

                    if(dataList.size()>0){
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               binding.llnCodeMedication.setVisibility(View.VISIBLE);
                               binding.llnKeepTrying.setVisibility(View.VISIBLE);
                               binding.btnDone.setVisibility(View.VISIBLE);
                               mProductAdapter = new DiagnosisAddResultAdapter(dataList,
                                       DiagnosisResultSearchActivity.this,
                                        lastActivity);
                               recyclerView.setLayoutManager(layoutManager);
                               recyclerView.setAdapter(mProductAdapter);
                               mProductAdapter.notifyDataSetChanged();
                               hideKeyboard(DiagnosisResultSearchActivity.this);
                           }
                       });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void SetOnClickResultStrength( ResultSearchProblemModel Obj , int position) {
        valueMedication  = Obj.getCode();
        valueCodeMedication  = Obj.getCode();
         strength  = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
             jsonObject  = jsonArray.getJSONObject(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jsonObject1 = jsonObject.getJSONArray("STRENGTHS_AND_FORMS");
            strength.add("Select");
            JSONArray jsonArrObj = jsonObject1.getJSONArray(0);
            for(int i =0 ; i<jsonArrObj.length(); i++){
                strength.add(String.valueOf(jsonArrObj.get(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiagnosisResultSearchActivity.this,
                R.layout.simple_item_spinner, strength);
        binding.editSearchMedication.setAdapter(adapter); // this will set list of values to spinner
        binding.editSearchMedication.setSelection(strength.indexOf(0));

    }


    public interface SetOnClickResult{
        void getDataOfSearchResult(String valueStrength , String codeValue , String Description );
    }
}