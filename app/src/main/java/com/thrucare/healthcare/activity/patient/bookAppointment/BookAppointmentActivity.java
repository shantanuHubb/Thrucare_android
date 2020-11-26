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
import com.thrucare.healthcare.databinding.ActivityBookAppointmentBinding;
import com.thrucare.healthcare.pojo.BookAppointmentFilter;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentActivity extends AppCompatActivity {

    private ActivityBookAppointmentBinding binding;
    private List<String> listLocation = new ArrayList<>();
    private List<String> listService = new ArrayList<>();
    private List<String> listSpeciality = new ArrayList<>();
    private ApiInterface mApiService;
    private List<BookAppointmentFilter.Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        // initiate the api service call
        mApiService = ApiUtils.getApiService();
        getBookAppointmentDataApiCalling();
        setListeners();


    }

    private void getBookAppointmentDataApiCalling() {

        Call<BookAppointmentFilter.ResponseBookAppointment> call = mApiService.getBookAppointmentFilter();

        call.enqueue(new Callback<BookAppointmentFilter.ResponseBookAppointment>() {
            @Override
            public void onResponse(Call<BookAppointmentFilter.ResponseBookAppointment> call,
                                   Response<BookAppointmentFilter.ResponseBookAppointment> response) {
                if (response.code() == 201 || response.code() == 200) {
                    BaseUtils.showToast(BookAppointmentActivity.this, "Success Book Appointment");
                    BookAppointmentFilter.ResponseBookAppointment resObj = response.body();

                    itemsList = resObj.getItems();
                    if (itemsList != null && itemsList.size() > 0) {
                        setDataOnView(itemsList);
                    }

                }
            }

            @Override
            public void onFailure(Call<BookAppointmentFilter.ResponseBookAppointment> call, Throwable t) {

            }
        });
    }

    private void setDataOnView(List<BookAppointmentFilter.Item> itemsList) {
        listLocation.clear();
        listLocation.add("Select");
        for (int i = 0; i < itemsList.size(); i++) {
            listLocation.add(itemsList.get(i).getLocation().getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookAppointmentActivity.this,
                R.layout.simple_item_spinner,
                listLocation);
        binding.spinnerLocationAppointment.setAdapter(adapter); // this will set list of values to spinner
        binding.spinnerLocationAppointment.setSelection(listLocation.indexOf(listLocation.get(0)));
    }

    private void setListeners() {
        binding.spinnerLocationAppointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position > 0) {
                    try {
                        setDataOnServiceAndSpeciality(itemsList, position - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    listService.clear();
                    listSpeciality.clear();
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(BookAppointmentActivity.this, R.layout.simple_item_spinner,
                            listService);
                    binding.spinnerServiceTypeAppointment.setAdapter(adapter1); // this will set list of values to spinner

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(BookAppointmentActivity.this, R.layout.simple_item_spinner,
                            listSpeciality);
                    binding.spinnerSpecialityAppointment.setAdapter(adapter2); // this will set list of values to spinner
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });

        binding.btnScheduleProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this, ScheduleAppointmentActivity.class));
            }
        });
    }

    private void setDataOnServiceAndSpeciality(List<BookAppointmentFilter.Item> itemsList, int position) {
        listService.clear();
        listSpeciality.clear();
        BookAppointmentFilter.Item itmObj = itemsList.get(position);
        List<BookAppointmentFilter.Type> listService = itmObj.getService().getType();

        for (int i = 0; i < listService.size(); i++) {
            this.listService.add(listService.get(i).getDisplay());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(BookAppointmentActivity.this, R.layout.simple_item_spinner,
                this.listService);
        binding.spinnerServiceTypeAppointment.setAdapter(adapter1); // this will set list of values to spinner
        binding.spinnerServiceTypeAppointment.setSelection(listService.indexOf(listService.get(0)));

        List<BookAppointmentFilter.Speciality> listSpeciality = itmObj.getSpeciality();
        for (int i = 0; i < listSpeciality.size(); i++) {
            this.listSpeciality.add(listSpeciality.get(i).getDisplay());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(BookAppointmentActivity.this, R.layout.simple_item_spinner,
                this.listSpeciality);
        binding.spinnerSpecialityAppointment.setAdapter(adapter2); // this will set list of values to spinner
        binding.spinnerSpecialityAppointment.setSelection(listSpeciality.indexOf(listSpeciality.get(0)));
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