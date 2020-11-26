package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.activity.provider.staff.StaffListAdapter;
import com.thrucare.healthcare.databinding.ActivityStepOneAppointmentBinding;
import com.thrucare.healthcare.databinding.ActivitySuccessBookApppointmentBinding;
import com.thrucare.healthcare.pojo.ServiceAssigment;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepOneAppointmentActivity extends AppCompatActivity {

    private ActivityStepOneAppointmentBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface mServiceApi;
    private StepOneBookAppointmentAdapter mServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStepOneAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Book Appointment");
        setContentView(view);
        
        // inti view
        intiView();

        getServiceAssignments();

    }

    private void getServiceAssignments() {
        Call<ServiceAssigment.ResponseServiceAssiegment> call  = mServiceApi.getServiceAssignments();

        call.enqueue(new Callback<ServiceAssigment.ResponseServiceAssiegment>() {
            @Override
            public void onResponse(Call<ServiceAssigment.ResponseServiceAssiegment> call,
                                   Response<ServiceAssigment.ResponseServiceAssiegment> response) {

                if(response.isSuccessful()){
                    BaseUtils.showToast(StepOneAppointmentActivity.this , "Success");
                    List<ServiceAssigment.Item> itemsList  = response.body().getItems();
                    mServiceAdapter = new StepOneBookAppointmentAdapter(itemsList,getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mServiceAdapter);
                    mServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ServiceAssigment.ResponseServiceAssiegment> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        recyclerView  = binding.recyclerViewBookStepOne;
        layoutManager = new LinearLayoutManager(this);
        mServiceApi  = ApiUtils.getApiService();
        
    }


}