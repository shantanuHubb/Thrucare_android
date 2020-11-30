package com.thrucare.healthcare.activity.provider.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.databinding.ActivityProviderServiveBinding;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;

public class ProviderServiceActivity extends AppCompatActivity {

    private ActivityProviderServiveBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface patientServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityProviderServiveBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        initView();
        getServiceApiCalling();
    }

    private void initView() {
        recyclerView = binding.rvProviderService;
        layoutManager = new LinearLayoutManager(this);
        patientServiceApi = PatientUtils.getPatientService();
    }
    private void getServiceApiCalling() {
    }

}