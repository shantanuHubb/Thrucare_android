package com.thrucare.healthcare.activity.patient.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.orders.AddLabTestActivity;
import com.thrucare.healthcare.activity.provider.orders.AddMedicationActivity;
import com.thrucare.healthcare.databinding.ActivityOrdersSubModuleBinding;
import com.thrucare.healthcare.pojo.PatientOrderType;
import com.thrucare.healthcare.pojo.PatientOrderTypeForOthers;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersSubModuleActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityOrdersSubModuleBinding binding;
    private ApiInterface mApiService;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private String orderType;
    private String orderValue;
    private OrderSubModuleAdapter mOrderAdapter;
    private String forProvider;

    static OrdersSubModuleActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersSubModuleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        //  inti view
        intiView(view);

        try {
            // getting sub layout value from the order fragment
            orderType = getIntent().getStringExtra(ConstantsUtils.SUB_LAYOUT_ORDER_TYPE);

            getOrderTypeValue(orderType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            forProvider = getIntent().getStringExtra(ConstantsUtils.For_Provider);
            if(forProvider.equalsIgnoreCase(ConstantsUtils.For_Provider)){
                 binding.llnAddOrderForProvider.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }

        if (ConstantsUtils.MEDICATION_ORDER.equalsIgnoreCase(orderType)) {
            // Api Call for getting order type
            getOrderTypeForMedicationApiCalling(orderValue);
        } else {
            // Api Call for getting order type
            getOrderTypeForOthersApiCalling(orderValue);
        }


    }

    public static  OrdersSubModuleActivity getInstance(){
        return  activity;
    }
    private void getOrderTypeForOthersApiCalling(String orderValue) {
        Call<PatientOrderTypeForOthers.ResponseOrderTypeOthers> call = mApiService.getPatientOrderForOtherType(orderValue);

        call.enqueue(new Callback<PatientOrderTypeForOthers.ResponseOrderTypeOthers>() {
            @Override
            public void onResponse(Call<PatientOrderTypeForOthers.ResponseOrderTypeOthers> call,
                                   Response<PatientOrderTypeForOthers.ResponseOrderTypeOthers> response) {

                if (response.isSuccessful()) {
                    List<PatientOrderTypeForOthers.Item> list = response.body().getItems();
                    mOrderAdapter = new OrderSubModuleAdapter(list, OrdersSubModuleActivity.this, orderType, forProvider);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mOrderAdapter);
                }
            }

            @Override
            public void onFailure(Call<PatientOrderTypeForOthers.ResponseOrderTypeOthers> call, Throwable t) {

            }
        });
    }

    private void getOrderTypeForMedicationApiCalling(String orderValue) {

        Call<PatientOrderType.ResponseOrderType> call = mApiService.getPatientOrderType(orderValue);

        call.enqueue(new Callback<PatientOrderType.ResponseOrderType>() {
            @Override
            public void onResponse(Call<PatientOrderType.ResponseOrderType> call,
                                   Response<PatientOrderType.ResponseOrderType> response) {

                if (response.isSuccessful()) {
                    List<PatientOrderType.Item> list = response.body().getItems();
                    mOrderAdapter = new OrderSubModuleAdapter(list, OrdersSubModuleActivity.this, orderType );
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mOrderAdapter);
                }
            }

            @Override
            public void onFailure(Call<PatientOrderType.ResponseOrderType> call, Throwable t) {

            }
        });

    }

    private void getOrderTypeValue(String orderType) {
        if (orderType.equalsIgnoreCase(ConstantsUtils.LAB_ORDER)) {
            orderValue = "patients/1/lab-orders";
            binding.tvHeaderOrderSubLayout.setText("Labs");
        } else if (orderType.equalsIgnoreCase(ConstantsUtils.RADIOLOGY_ORDER)) {
            orderValue = "patients/1/radiology-orders";
            binding.tvHeaderOrderSubLayout.setText("Radiology");
        } else if (orderType.equalsIgnoreCase(ConstantsUtils.VISION_ORDER)) {
            orderValue = "patients/1/vision-orders";
            binding.tvHeaderOrderSubLayout.setText("Vision");
        } else if (orderType.equalsIgnoreCase(ConstantsUtils.REFFERAL_ORDER)) {
            orderValue = "patients/1/referral-orders";
            binding.tvHeaderOrderSubLayout.setText("Referrals");
        } else {
            binding.tvHeaderOrderSubLayout.setText("Medications");
            orderValue = "patients/1/medication-orders";
        }
    }

    private void intiView(View view) {
        mApiService = ApiUtils.getApiService();
        recyclerView = view.findViewById(R.id.recyclerView_sub_layout);
        layoutManager = new LinearLayoutManager(this);
        binding.llnAddOrderForProvider.setOnClickListener(this);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lln_add_order_for_provider:
                if (orderType.equalsIgnoreCase(ConstantsUtils.LAB_ORDER)) {
                    startActivity(new Intent(OrdersSubModuleActivity.this , AddLabTestActivity.class)
                            .putExtra(ConstantsUtils.ORDER_TYPE , orderType));
                } else if (orderType.equalsIgnoreCase(ConstantsUtils.RADIOLOGY_ORDER)) {
                    startActivity(new Intent(OrdersSubModuleActivity.this , AddLabTestActivity.class)
                            .putExtra(ConstantsUtils.ORDER_TYPE , orderType));
                } else if (orderType.equalsIgnoreCase(ConstantsUtils.VISION_ORDER)) {

                } else if (orderType.equalsIgnoreCase(ConstantsUtils.REFFERAL_ORDER)) {
                    PreferenceUtils.insertData(this, "PerformerName" , "") ;
                    PreferenceUtils.insertData(this, "PerformerType" , "") ;
                    PreferenceUtils.insertData(this, "Procedure" , "") ;
                    PreferenceUtils.insertData(this, "Status" , "");
                    startActivity(new Intent(OrdersSubModuleActivity.this , AddLabTestActivity.class)
                            .putExtra(ConstantsUtils.ORDER_TYPE , orderType));
                } else {
                  //  binding.tvHeaderOrderSubLayout.setText("Medications");
                    startActivity(new Intent(OrdersSubModuleActivity.this , AddMedicationActivity.class));
                }
                break;
        }
    }
}