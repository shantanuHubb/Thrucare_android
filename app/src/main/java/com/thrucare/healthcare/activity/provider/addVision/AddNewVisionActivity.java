package com.thrucare.healthcare.activity.provider.addVision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.activity.provider.AllergiesReportProviderActivity;
import com.thrucare.healthcare.databinding.ActivityAddNewVisionBinding;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
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

public class AddNewVisionActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddNewVisionBinding binding;
    private ApiInterface mServiceApi;
    private List<String> optionsListStatus = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private Calendar myCalendar;
    private int selectionCodePositionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewVisionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        // inti view
        intiView();

        // get status api calling
        getStatusApiCalling();

        // set calendar
        setDateCalendar();

        // set Listeners
        setListeners();
    }

    private void setListeners() {
        binding.spinnerStatusVision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {
                selectionCodePositionStatus = selectedPosition;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDateCalendar() {
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
        binding.edtDateVision.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewVisionActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void intiView() {
        binding.llnLeftVision.setOnClickListener(this);
        binding.llnRightVision.setOnClickListener(this);
        mServiceApi  = ApiUtils.getApiService();
        binding.btnSubmitVision.setOnClickListener(this);
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.edtDateVision.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lln_left_vision:
                   startActivity(new Intent(AddNewVisionActivity.this , EyeVisionActivity.class)
                    .putExtra(ConstantsUtils.EYE_TYPE , ConstantsUtils.LEFT));
                break;


            case R.id.lln_right_vision:
                startActivity(new Intent(AddNewVisionActivity.this , EyeVisionActivity.class)
                        .putExtra(ConstantsUtils.EYE_TYPE , ConstantsUtils.RIGHT));
                break;

            case R.id.btn_submit_vision:

                saveVisionApiCalling();
                break;
        }
    }

    private void saveVisionApiCalling() {
        JsonObject jsonMainObject  = new JsonObject();
        jsonMainObject.addProperty("category" , binding.edtVisionCategory.getText().toString());

        JsonObject jsonObjectStatus  = new JsonObject();
        jsonObjectStatus.addProperty("code"  , optionsListStatusModel.get(selectionCodePositionStatus).getCode());
        jsonMainObject.add("status" , jsonObjectStatus);
        jsonMainObject.addProperty("created_date" , binding.edtDateVision.getText().toString());
        jsonMainObject.addProperty("created_date" , binding.edtDateVision.getText().toString());
        JsonObject jsonObjectProvider  = new JsonObject();
        jsonObjectProvider.addProperty("uuid" , "123e4567-e89b-12d3-a456-426614174000");
        jsonMainObject.add("provider" , jsonObjectProvider);
        jsonMainObject.addProperty("notes" , binding.edtNotesVision.getText().toString());

        Call<ResponseBody>  call  = mServiceApi.saveVisionData("application/json" , jsonMainObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    BaseUtils.showToast(AddNewVisionActivity.this , "success from add new vision");
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    private void getStatusApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mServiceApi.getAllergiesCriticality();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewVisionActivity.this,
                            R.layout.simple_item_spinner, optionsListStatus);
                    binding.spinnerStatusVision.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusVision.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                    selectionCodePositionStatus  = 0;
                }



            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}