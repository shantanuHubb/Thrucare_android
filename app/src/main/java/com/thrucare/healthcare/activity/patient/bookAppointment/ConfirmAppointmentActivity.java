package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.dependents.DependentsAdapter;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.databinding.ActivityConfirmAppointmentBinding;
import com.thrucare.healthcare.pojo.DependentsList;
import com.thrucare.healthcare.pojo.DependentsModel;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityConfirmAppointmentBinding binding;
    private ApiInterface mApiService;
    private List<DependentsModel> optionsList = new ArrayList<>();
    private List<String > optionsListStr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Book an appointment");
        setContentView(view);

        // inti view
        intiView();


        // get dependents list
        getDependentsListApiCalling();

        setListeners();

    }

    private void setListeners() {
        binding.spinnerDependentsName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position!=0){
                    binding.edtMailDepe.setText(optionsList.get(position-1).getDependentEmail());
                }else {
                    binding.edtMailDepe.setText("Johnonepatient@gmail.com");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void intiView() {
        mApiService  = ApiUtils.getApiService();
        binding.btnBook.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_book:
                if(PreferenceUtils.retriveData(this , "BOOK").equalsIgnoreCase("ReSchedule")){
                    reScheduleApiCalling();
                }else {
                    saveBookAppointmentApiCalling();
                }

                break;
        }
    }

    private void saveBookAppointmentApiCalling() {
        Call<ResponseBody> call = mApiService.saveBookAppointment();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(ConfirmAppointmentActivity.this,
                            SuccessBookApppointmentActivity.class));
                    BaseUtils.showToast(ConfirmAppointmentActivity.this ,
                            "Your Appointment has been booked successfully");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
    private void reScheduleApiCalling() {
        Call<ResponseBody> call = mApiService.rescheduleBooking();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(ConfirmAppointmentActivity.this,
                            SuccessBookApppointmentActivity.class));
                    BaseUtils.showToast(ConfirmAppointmentActivity.this ,
                            "Your Appointment has been booked Reschedule successfully");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    private void getDependentsListApiCalling() {

        Call<DependentsList.ResponseListDependents> call  = mApiService.getListOfPatientDependents();

        call.enqueue(new Callback<DependentsList.ResponseListDependents>() {
            @Override
            public void onResponse(Call<DependentsList.ResponseListDependents> call,
                                   Response<DependentsList.ResponseListDependents> response) {

                if(response.isSuccessful()){
                    List<DependentsList.Item> itemList  = response.body().getItems();


                    optionsListStr.clear();
                    optionsListStr.add("John One patient" );
                    for (int i = 0; i < itemList.size(); i++) {
                        optionsListStr.add(itemList.get(i).getFirstName() +" "+
                                itemList.get(i).getMiddleName() +" "+ itemList.get(i).getLastName()  );
                        optionsList.add(new DependentsModel(   itemList.get(i).getFirstName() +" "+
                                itemList.get(i).getMiddleName() +" "+ itemList.get(i).getLastName()  , itemList.get(i).getEmail()));

                         }
                    if (optionsListStr.size() > 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConfirmAppointmentActivity.this,
                                R.layout.simple_item_spinner, optionsListStr);
                        binding.spinnerDependentsName.setAdapter(adapter); // this will set list of values to spinner
                        binding.spinnerDependentsName.setSelection(optionsListStr.indexOf(optionsListStr.get(0)));
                    }


                }
            }

            @Override
            public void onFailure(Call<DependentsList.ResponseListDependents> call, Throwable t) {

            }
        });

    }
}