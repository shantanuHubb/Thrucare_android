package com.thrucare.healthcare.activity.patient.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.qualification.QualificationListActivity;
import com.thrucare.healthcare.activity.provider.qualification.QualificationListAdapter;
import com.thrucare.healthcare.databinding.ActivityEmergencyContactListBinding;
import com.thrucare.healthcare.pojo.EmergencyContactList;
import com.thrucare.healthcare.pojo.modelClasses.PatientEmergencyContactList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class EmergencyContactListActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityEmergencyContactListBinding binding;
    static  EmergencyContactListActivity activity;
    private ApiInterface PatientServiceApi;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<PatientEmergencyContactList.Item> itemList = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private EmergencyContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyContactListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        PatientServiceApi = PatientUtils.getPatientService();
        initView();
        getEmergencyContactList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getEmergencyContactList();
    }

    private void initView() {
        recyclerView = binding.recyclerViewEmergencyList;
        layoutManager = new LinearLayoutManager(activity);
        binding.imgNewEmergency.setOnClickListener(this);
    }
    private void getEmergencyContactList() {
        Call<PatientEmergencyContactList> call = PatientServiceApi.getPatientEmergencyContact(PreferenceUtils.retriveData(this , "PUUID_PATIENT") , ConstantsUtils.API_KEY);
        call.enqueue(new Callback<PatientEmergencyContactList>() {
            @Override
            public void onResponse(Call<PatientEmergencyContactList> call, Response<PatientEmergencyContactList> response) {
                if(response.code() == 200)
                {
                    //BaseUtils.showToast(EmergencyContactListActivity.this , "Success");
                    itemList = response.body().getItems();
                    mAdapter = new EmergencyContactAdapter(itemList , activity);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter.notifyDataSetChanged();
                }
                else
                    BaseUtils.showToast(EmergencyContactListActivity.this , "Failed error code "+ response.code());
            }

            @Override
            public void onFailure(Call<PatientEmergencyContactList> call, Throwable t) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(EmergencyContactListActivity.this , AddEmergencyContactActivity.class);
        startActivity(intent);
    }
}