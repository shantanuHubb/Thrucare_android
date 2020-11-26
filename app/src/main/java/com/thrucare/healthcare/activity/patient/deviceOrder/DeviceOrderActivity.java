package com.thrucare.healthcare.activity.patient.deviceOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.dependents.DependentsAdapter;
import com.thrucare.healthcare.activity.provider.deviceOrder.AddNewDeviceOrderActivity;
import com.thrucare.healthcare.activity.provider.insurance.AddNewInsuranceActivity;
import com.thrucare.healthcare.databinding.ActivityDeviceOrderBinding;
import com.thrucare.healthcare.databinding.ActivityOrdersSubModuleBinding;
import com.thrucare.healthcare.pojo.DeviceList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDeviceOrderBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private String userType;
    private ApiInterface mServiceApi;
    private DeviceListAdapter mDeviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        
        
        // intiView
        intiView();

        // method to call a api for device list
        getDeviceListApiCalling();
    }

    private void getDeviceListApiCalling() {
        Call<DeviceList.ResponseListDeviceOrder> call  = mServiceApi.getDeviceListOrder();
        call.enqueue(new Callback<DeviceList.ResponseListDeviceOrder>() {
            @Override
            public void onResponse(Call<DeviceList.ResponseListDeviceOrder>
                                           call, Response<DeviceList.ResponseListDeviceOrder> response) {

                if(response.isSuccessful()){
                    BaseUtils.showToast(DeviceOrderActivity.this, "Success");
                    List<DeviceList.Item> itemsList  = response.body().getItems();

                    mDeviceAdapter = new DeviceListAdapter(itemsList, DeviceOrderActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mDeviceAdapter);
                    mDeviceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DeviceList.ResponseListDeviceOrder> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        try{
           userType  = getIntent().getStringExtra(ConstantsUtils.For_Provider) ;
        }catch (Exception e){

        }
        if(userType.equalsIgnoreCase(ConstantsUtils.For_Provider)){
            binding.imgNewDeviceOrder.setVisibility(View.VISIBLE);
        }
        recyclerView  = binding.recyclerViewDeviceList;
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mServiceApi  = ApiUtils.getApiService();

        binding.imgNewDeviceOrder.setOnClickListener(this);
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
            case R.id.img_new_device_order:
                startActivity(new Intent(DeviceOrderActivity.this, AddNewDeviceOrderActivity.class));
                break;
        }
    }
}