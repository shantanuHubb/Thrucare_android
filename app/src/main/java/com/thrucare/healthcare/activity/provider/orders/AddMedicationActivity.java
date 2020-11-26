package com.thrucare.healthcare.activity.provider.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.DiagnosisResultSearchActivity;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.adapter.DiagnosisAddResultAdapter;
import com.thrucare.healthcare.databinding.ActivityAddMedicationBinding;
import com.thrucare.healthcare.pojo.AddMedication;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ResponseMedicationStatusAPI;
import com.thrucare.healthcare.pojo.ResultSearchProblemModel;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicationActivity extends AppCompatActivity implements View.OnClickListener  ,
        DiagnosisAddResultAdapter.SetOnClickResult , DiagnosisResultSearchActivity.SetOnClickResult ,
        AllergiesSearchAdpater.SetOnClickAllergiesSearch {
    private List<ReactionModel> list = new ArrayList<>();
    private ActivityAddMedicationBinding binding;
    private static AddMedicationActivity activity;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllergiesReactionAdapter mReasonAdapter;
    private ApiInterface mApisService;
    private String selectedCodeForProblem;
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private List<String> optionsListStatus  = new ArrayList<>();
    private Calendar myCalendar;
    String spinnerStatus,medicationCode,createdDate,dosage,dispansationQuantity,dispensationRefill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMedicationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);


        setListener();
        mApisService = ApiUtils.getApiService();
         getStatusMedicationApiCalling();
        recyclerView  =  view.findViewById(R.id.recyclerViewMedicationReaction_order);
        layoutManager = new LinearLayoutManager(this);

        list.add(new ReactionModel(1, "Reason" , ""));
        mReasonAdapter = new AllergiesReactionAdapter(list,
                this , ConstantsUtils.MEDICATION_ADD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mReasonAdapter);
        mReasonAdapter.notifyDataSetChanged();



        binding.tvSaveAddMedicationReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosage = binding.edtMedicationDosage.getText().toString();
                dispansationQuantity = binding.edtDespensationQuantity.getText().toString();
                dispensationRefill = binding.edtDespensationRefills.getText().toString();
                saveMedicationReport(medicationCode,list,dosage,dispansationQuantity,dispensationRefill,createdDate,spinnerStatus);
                AddMedicationActivity.this.onBackPressed();
            }
        });


    }

    private void saveMedicationReport(String medicationCode, List<ReactionModel> list, String dosage, String dispansationQuantity, String dispensationRefill, String createdDate, String spinnerStatus) {
        JsonObject object = new JsonObject();


        JsonObject medicationObject = new JsonObject();
        medicationObject.addProperty("code",medicationCode);

        JsonObject reasonObject = new JsonObject();

        for (int i = 0; i<list.size();i++){
            reasonObject.addProperty("code",list.get(i).getReactionValueForAPI());
            Log.d("li",list.get(i).getReactionValueForAPI());
        }
        JsonArray array = new JsonArray();
        array.add(reasonObject);

        JsonObject dispensetionObject = new JsonObject();
        dispensetionObject.addProperty("quantity",dispansationQuantity);
        dispensetionObject.addProperty("refills",dispensationRefill);

        JsonObject providerObject = new JsonObject();
        providerObject.addProperty("uuid","123e4567-e89b-12d3-a456-426614174000");

        try {
            object.add("medication",medicationObject);
            object.add("reason",array);
            object.addProperty("dosage",dosage);
            object.add("dispensation",dispensetionObject);
            object.addProperty("created_date",createdDate);
            object.add("provider",providerObject);
            Log.d("obj",object.toString());
    } catch (Exception e) {
            e.printStackTrace();
        }

        Call<AddMedication> call = mApisService.addMedication(getResources().getString(R.string.application_json),object);
        call.enqueue(new Callback<AddMedication>() {
            @Override
            public void onResponse(Call<AddMedication> call, Response<AddMedication> response) {
                Log.d("res",response.toString());
                Toast.makeText(AddMedicationActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddMedication> call, Throwable t) {

            }
        });

    }

    private void getStatusMedicationApiCalling() {
        Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call  = mApisService.getMedeicationStatus();
        call.enqueue(new Callback<ResponseMedicationStatusAPI.ResponseMedicationStatus>() {
            @Override
            public void onResponse(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call,
                                   Response<ResponseMedicationStatusAPI.ResponseMedicationStatus> response) {
                List<ResponseMedicationStatusAPI.Item> reObj  = response.body().getItems();

                for(int i =0 ; i<reObj.size() ; i++){
                    optionsListStatus.add(reObj.get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.get(i).getCode(),
                            reObj.get(i).getDisplay()));
                }
                if(optionsListStatus.size()>0){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMedicationActivity.this, R.layout.simple_item_spinner,
                            optionsListStatus);
                    binding.spinnerStatusMedicationOrder.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusMedicationOrder.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                    binding.spinnerStatusMedicationOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerStatus = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public static AddMedicationActivity getInstance(){
        return activity;
    }
    private void setListener() {
        binding.edtMedicationOrder.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        binding.dateMedicationProviderOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddMedicationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateMedicationProviderOrder.setText(sdf.format(myCalendar.getTime()));
        createdDate = sdf.format(myCalendar.getTime());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edt_medication_order:
                startActivity(new Intent(AddMedicationActivity.this, DiagnosisResultSearchActivity.class)
                        .putExtra("LAST_ACTIVITY", "MEDICATION_ADD"));
                break;
        }
    }


    @Override
    public void getDataOfSearchResult(ResultSearchProblemModel Obj) {
       // binding.edtMedicationOrder.setText(Obj.getDescription());
    }

    @Override
    public void getDataOfSearchResult(String valueStrength, String codeValue, String Description) {
        binding.edtMedicationOrder.setText(Description+ " "+ valueStrength);
        medicationCode = codeValue;
    }

    @Override
    public void getValueForSubstance(String display) {

    }

    @Override
    public void getValueForReaction(String display) {
        try{
            int adpaterPosition  = PreferenceManager.getDefaultSharedPreferences(this).getInt("ADAPTER_POSITION" , 0);
            list.get(adpaterPosition).setReactionValueForAPI(display);
            mReasonAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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