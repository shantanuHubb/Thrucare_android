package com.thrucare.healthcare.activity.provider.addImnunization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ExpandableListView;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.immunization.ImmunizationFragment;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.activity.provider.AllergiesSearchActivity;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAddNewImmunizationBinding;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
import com.thrucare.healthcare.pojo.ImmunizationList;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewImmunizationActivity extends AppCompatActivity implements
        View.OnClickListener , AllergiesSearchAdpater.SetOnClickPerformer {

    private ActivityAddNewImmunizationBinding binding;
      static AddNewImmunizationActivity activity;
    private ApiInterface mApisService;
    private List<String> optionsListOrigin  = new ArrayList<>();
    private ArrayList<ModelCodeAndDisplay> optionsListStatusModel  = new ArrayList<>();
    private List<String> optionsListStatus = new ArrayList<>();
    private Calendar myCalendar;
    private String valueForAddOrUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewImmunizationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        activity  = this;


        // intiView
        intiView();

        getIntentDataAndSetValue();
        setListeners();
        getOriginApiCalling();

        getStatusApiCalling();

    }

    private void getIntentDataAndSetValue() {
        try{
            valueForAddOrUpdate  = getIntent().getStringExtra(ConstantsUtils.IMMU_ADD_OR_UPDATE);
            if(valueForAddOrUpdate.equalsIgnoreCase(ConstantsUtils.IMMU_ADD_OR_UPDATE_UPDATE)){
                ImmunizationList.Item itemObj  = ImmunizationFragment.getInstance().getItem();
                binding.edtVaccineImmunization.setText(itemObj.getVaccine().getDisplay());
                binding.edtDateImmunization.setText(itemObj.getOccurDate());
                binding.btnSubmitImmunization.setText("Update");
            }else if(valueForAddOrUpdate.equalsIgnoreCase(ConstantsUtils.IMMU__ADD_OR_UPDATE_ADD)){
                BaseUtils.showToast(this , "add");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListeners() {
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
        binding.edtDateImmunization.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewImmunizationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.edtDateImmunization.setText(sdf.format(myCalendar.getTime()));
    }
    public static AddNewImmunizationActivity getInstance(){
        return activity ;
    }
    private void intiView() {
        binding.edtVaccineImmunization.setOnClickListener(this);
        mApisService  = ApiUtils.getApiService();
          binding.btnSubmitImmunization.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edt_vaccine_immunization:
                startActivity(new Intent(this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "ADD_VACCINE"));
                break;
                
                
            case R.id.btn_submit_immunization:
                saveNewImnunizationApicalling();
                onBackPressed();
                break;
        }
    }

    private void saveNewImnunizationApicalling() {
        JsonObject jsonObject = new JsonObject();
        Call<ResponseBody> call  = mApisService.saveImmunization("application/json" , jsonObject );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(String displayName, String displayCode) {

    }

    @Override
    public void onClickProcedure(String displayName, String displayCode) {
        binding.edtVaccineImmunization.setText(displayName);
    }

    private void getOriginApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getOriginImmunization();
        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListOrigin.add(reObj.getItems().get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListOrigin.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewImmunizationActivity.this,
                            R.layout.simple_item_spinner, optionsListOrigin);
                    binding.spinnerOriginImmunization.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerOriginImmunization.setSelection(optionsListOrigin.indexOf(optionsListOrigin.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private void getStatusApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getStatusImmunization();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewImmunizationActivity.this,
                            R.layout.simple_item_spinner, optionsListStatus);
                    binding.spinnerStatusImmunization.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusImmunization.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}