package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.diagnosis.DiagnosisFragment;
import com.thrucare.healthcare.adapter.DiagnosisAddResultAdapter;
import com.thrucare.healthcare.databinding.ActivityAddDiagnosisBinding;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
import com.thrucare.healthcare.pojo.MedicationPojp;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.PatientDiagnosis;
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

public class AddDiagnosisActivity extends AppCompatActivity implements DiagnosisAddResultAdapter.SetOnClickResult, EvidenceMedicalActivity.OnClickOnContinue {

    private ActivityAddDiagnosisBinding binding;
    private List<String> optionsListSeverity = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListSeverityModel = new ArrayList<>();

    private List<String> optionsListStatus = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private ApiInterface mApisService;
    public static AddDiagnosisActivity activity;

    private String selectedCodeForProblem = "";
    private String selectedCodeForSeverity;
    private String selectedCodeForStatus;
    private Calendar myCalendar;
    private MedicationPojp medicationObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDiagnosisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        mApisService = ApiUtils.getApiService();

        getIntentAndSetValue();
        binding.edtProblemDiagnosis.setOnClickListener(view1 -> {
            startActivity(new Intent(AddDiagnosisActivity.this, DiagnosisResultSearchActivity.class).
                    putExtra("LAST_ACTIVITY", "DIAGNOSIS"));
        });

        getSeverityApiCalling();
        getStatusApiCalling();


        setListners();


    }

    private void getIntentAndSetValue() {
        try{
            String value   = getIntent().getStringExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE);
            if(value.equalsIgnoreCase(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_UPDATE)){
                medicationObject  = DiagnosisFragment.getDiagnosisFragment().getDataForUpdate();
                binding.tvAddMoreDiagnosis.setVisibility(View.GONE);
                binding.tvSaveAddDiagnosis.setText("Update");
                binding.tvSaveAddDiagnosis.setTextColor(getColor(R.color.white));
                binding.tvSaveAddDiagnosis.setBackgroundColor(getColor(R.color.colorAccent));
                binding.edtProblemDiagnosis.setText(medicationObject.getTvName());
                binding.datePickerAddDiagnosis.setText(medicationObject.getTvDate());
                binding.spinnerSeverity.setPrompt(medicationObject.getTvReason());
                selectedCodeForProblem  = medicationObject.getTvReason();
            }else if(value.equalsIgnoreCase(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_ADD)) {
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void setListners() {
        binding.spinnerSeverity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("dataSelected", optionsListSeverityModel.get(i).getDisplayName());
                selectedCodeForSeverity = optionsListSeverityModel.get(i).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("dataSelected", optionsListStatusModel.get(i).getDisplayName());
                selectedCodeForStatus = optionsListStatusModel.get(i).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        binding.tvSaveAddDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCodeForProblem.length() > 0) {
                    saveAddDiagnosisApiCalling();
                    //showToast("Added Diagnosis successfully");
                    AddDiagnosisActivity.this.onBackPressed();
                } else {
                    showToast("Please Select the problem");
                }
            }
        });

        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDiagnosisActivity.this, EvidenceMedicalActivity.class));
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
        binding.datePickerAddDiagnosis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddDiagnosisActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.tvAddMoreDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCodeForProblem.length() > 0) {
                    saveAddDiagnosisApiCalling();
                    startActivity(new Intent(AddDiagnosisActivity.this, AddDiagnosisActivity.class));
                    finish();
                } else {
                    showToast("Please Select the problem");
                }
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.datePickerAddDiagnosis.setText(sdf.format(myCalendar.getTime()));
    }
    private void saveAddDiagnosisApiCalling() {
        JsonObject reqObj = new JsonObject();
        JsonObject problemCode = new JsonObject();
        problemCode.addProperty("code", selectedCodeForProblem);
        reqObj.add("problem", problemCode);


        JsonObject severityCode = new JsonObject();
        problemCode.addProperty("code", selectedCodeForSeverity);
        reqObj.add("severity", severityCode);

        JsonObject statusCode = new JsonObject();
        problemCode.addProperty("code", selectedCodeForStatus);
        reqObj.add("status", statusCode);
        reqObj.addProperty("onset_date", "10/09/2020");
        reqObj.add("evidence", null);


        JsonObject providerUuid = new JsonObject();
        problemCode.addProperty("uuid", "123e4567-e89b-12d3-a456-426614174000");
        reqObj.add("provider", providerUuid);


        Call<PatientDiagnosis.PatientDiagnosisResponse> call = mApisService.saveAddDiagnosis("application/json",
                reqObj);


        call.enqueue(new Callback<PatientDiagnosis.PatientDiagnosisResponse>() {
            @Override
            public void onResponse(Call<PatientDiagnosis.PatientDiagnosisResponse> call, Response<PatientDiagnosis.PatientDiagnosisResponse> response) {

                Log.d("Respnsecode", String.valueOf(response.code()));
                if (response.code() == 201) {
                    showToast("Added Diagnosis successfully");
                }
            }

            @Override
            public void onFailure(Call<PatientDiagnosis.PatientDiagnosisResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddDiagnosisActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static AddDiagnosisActivity getInstance() {
        return activity;
    }

    private void getSeverityApiCalling() {

        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getSeverity();

        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListSeverity.add(reObj.getItems().get(i).getDisplay());
                    optionsListSeverityModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListSeverity.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddDiagnosisActivity.this, R.layout.simple_item_spinner, optionsListSeverity);
                    binding.spinnerSeverity.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerSeverity.setSelection(optionsListSeverity.indexOf(optionsListSeverity.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private void getStatusApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getStatus();
        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListStatus.add(reObj.getItems().get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListStatus.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddDiagnosisActivity.this, R.layout.simple_item_spinner, optionsListStatus);
                    binding.spinnerStatus.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerStatus.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getDataOfSearchResult(ResultSearchProblemModel Obj) {
        Log.d("Obj", Obj.getCode());
        selectedCodeForProblem = Obj.getCode();
        binding.edtProblemDiagnosis.setText(Obj.getDescription());


    }

    @Override
    public void getSelectedMedicalReport(StringBuffer value) {
        binding.editEvidence.setText(value);
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