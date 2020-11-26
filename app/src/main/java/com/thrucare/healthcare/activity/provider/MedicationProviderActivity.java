package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.medications.MedicationFragment;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.adapter.DiagnosisAddResultAdapter;
import com.thrucare.healthcare.databinding.ActivityMedicationProviderBinding;
import com.thrucare.healthcare.pojo.MedicationPojp;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.PatientMedication;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ResponseMedicationStatusAPI;
import com.thrucare.healthcare.pojo.ResultSearchProblemModel;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicationProviderActivity extends AppCompatActivity implements DiagnosisResultSearchActivity.SetOnClickResult ,
        DiagnosisAddResultAdapter.SetOnClickResult , AllergiesSearchAdpater.SetOnClickAllergiesSearch {

    private ActivityMedicationProviderBinding binding;
    public  static MedicationProviderActivity activity;
    private ApiInterface mApisService;
    private List<String> optionsListStatus  = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();


    private List<String> optionsListReasons  = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListReasonsModel = new ArrayList<>();
    private String selectedCodeForProblem;
    private Calendar myCalendar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<ReactionModel> list = new ArrayList<>();
    private AllergiesReactionAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationProviderBinding.inflate(getLayoutInflater());
        activity  = this;
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setListeners();
        mApisService = ApiUtils.getApiService();

        getIntentValueAndSet();
        getStatusMedicationApiCalling();
        recyclerView  =  view.findViewById(R.id.recyclerViewMedicationReaction);
        layoutManager = new LinearLayoutManager(this);
        list.add(new ReactionModel(1, "Reason" , ""));
        mProductAdapter = new AllergiesReactionAdapter(list,
                this , ConstantsUtils.MEDICATION);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
    }

    private void getIntentValueAndSet() {
        try{
            String value  = getIntent().getStringExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE);
            if(value.equalsIgnoreCase(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_UPDATE)){
                binding.tvAddMoreMedication.setVisibility(View.GONE);
                binding.tvSaveAddMedicationReport.setBackgroundColor(getColor(R.color.colorAccent));
                binding.tvSaveAddMedicationReport.setTextColor(getColor(R.color.white));
                binding.tvSaveAddMedicationReport.setText("Update");

                MedicationPojp medicationPojp  = MedicationFragment.getInstance().getMedication();
                binding.dateMedicationProvider.setText(medicationPojp.getTvDate());
                binding.edtMedication.setText(medicationPojp.getTvName());
                selectedCodeForProblem   = medicationPojp.getTvName();
                list.add(new ReactionModel(1, "Reason" , medicationPojp.getTvReason()));
                mProductAdapter = new AllergiesReactionAdapter(list,
                        this , ConstantsUtils.MEDICATION);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mProductAdapter);
                mProductAdapter.notifyDataSetChanged();

            } else if(value.equalsIgnoreCase(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_ADD)){
                BaseUtils.showToast(this, "add");
            }
        }catch (Exception e){

        }
    }


    public static MedicationProviderActivity getInstance(){
        return activity;
    }
    private void setListeners() {
        binding.edtMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicationProviderActivity.this, DiagnosisResultSearchActivity.class)
                        .putExtra("LAST_ACTIVITY", "MEDICATION"));
            }
        });


        binding.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.llnReasonAdd.getVisibility()==View.VISIBLE){
                    binding.llnReasonAdd.setVisibility(View.GONE);
                }else if(binding.llnReasonAdd1.getVisibility()==View.VISIBLE){
                    binding.llnReasonAdd1.setVisibility(View.GONE);
                    binding.imgPlus.setImageDrawable(getDrawable(R.drawable.plus));
                }
                else {
                    binding.llnReasonAdd.setVisibility(View.VISIBLE);
                    binding.imgPlus.setImageDrawable(getDrawable(R.drawable.minus));
                    if(optionsListReasons.size()>0){
                        binding.llnReasonAdd.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicationProviderActivity.this,
                                R.layout.simple_item_spinner, optionsListReasons);
                        binding.spinnerReason1.setAdapter(adapter); // this will set list of values to spinner
                        binding.spinnerReason1.setSelection(optionsListReasons.indexOf(optionsListReasons.get(0)));
                    }
                }
            }
        });


      binding.imgPlus1.setOnClickListener(new View.OnClickListener(){

          @Override
          public void onClick(View view) {
              if(optionsListReasons.size()>0){
                  binding.llnReasonAdd1.setVisibility(View.VISIBLE);
                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicationProviderActivity.this,
                          R.layout.simple_item_spinner, optionsListReasons);
                  binding.spinnerReasonMedication.setAdapter(adapter); // this will set list of values to spinner
                  binding.spinnerReasonMedication.setSelection(optionsListReasons.indexOf(optionsListReasons.get(0)));
              }
          }
      });

        binding.tvSaveAddMedicationReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(selectedCodeForProblem!=null){
                       if(selectedCodeForProblem.length()>0){
                           saveMedicationApiCalling();
                           MedicationProviderActivity.this.onBackPressed();
                       }else {
                           showToast("Please select the medication");
                       }
                   }
            }
        });


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
        binding.dateMedicationProvider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(MedicationProviderActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.tvAddMoreMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCodeForProblem!=null){
                    if(selectedCodeForProblem.length()>0){
                        saveMedicationApiCalling();
                        startActivity(new Intent(MedicationProviderActivity.this , MedicationProviderActivity.class));
                        finish();
                    }else {
                        showToast("Please select the medication");
                    }
                }

            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateMedicationProvider.setText(sdf.format(myCalendar.getTime()));
    }
    private void saveMedicationApiCalling() {
        JsonObject reqObj  = new JsonObject();

        JsonObject medicationObj  = new JsonObject();
        medicationObj.addProperty("code", "51252");
        reqObj.add("medication", medicationObj);

        JsonArray jsonArrayReasons  = new JsonArray();
        JsonObject reasonObj  = new JsonObject();
        reasonObj.addProperty("code", "5551111");
        jsonArrayReasons.add(reasonObj);
        reqObj.add("reason", jsonArrayReasons);


        JsonObject statusObj  = new JsonObject();
        statusObj.addProperty("code", "51252");
        reqObj.add("status", statusObj);
        reqObj.addProperty("effective_date", "09/01/2020");


        JsonObject providerObj  = new JsonObject();
        providerObj.addProperty("uuid", "123e4567-e89b-12d3-a456-426614174000");
        reqObj.add("provider", providerObj);


        Call<PatientMedication.PatientMedicationRes>  call  = mApisService.saveAddMedications("application/json",
                                                                          reqObj);

        call.enqueue(new Callback<PatientMedication.PatientMedicationRes>() {
            @Override
            public void onResponse(Call<PatientMedication.PatientMedicationRes> call,
                                   Response<PatientMedication.PatientMedicationRes> response) {
                if(response.code()==200){
                    showToast("Medication Added Successfully");
                }
                if(response.code()==201){
                    showToast("Medication Added Successfully");
                }
            }

            @Override
            public void onFailure(Call<PatientMedication.PatientMedicationRes> call, Throwable t) {
                 t.printStackTrace();
            }
        });


    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicationProviderActivity.this, R.layout.simple_item_spinner, optionsListStatus);
                    binding.spinnerStatusMedication.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusMedication.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));

                }

            }

            @Override
            public void onFailure(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getDataOfSearchResult(String valueStrength, String codeValue, String Description) {
        selectedCodeForProblem = codeValue;
        binding.edtMedication.setText(Description+ " "+ valueStrength);
    }

    @Override
    public void getDataOfSearchResult(ResultSearchProblemModel Obj) {

    }

    @Override
    public void getValueForSubstance(String display) {

    }

    @Override
    public void getValueForReaction(String display) {
        try{
            int adpaterPosition  = PreferenceManager.getDefaultSharedPreferences(this).getInt("ADAPTER_POSITION" , 0);
            list.get(adpaterPosition).setReactionValueForAPI(display);
            mProductAdapter.notifyDataSetChanged();
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