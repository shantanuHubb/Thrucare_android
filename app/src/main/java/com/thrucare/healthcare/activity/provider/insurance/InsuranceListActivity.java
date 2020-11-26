package com.thrucare.healthcare.activity.provider.insurance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityInsuranceListBinding;
import com.thrucare.healthcare.pojo.modelClasses.PatientInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderInsuranceList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class InsuranceListActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityInsuranceListBinding binding;
    static InsuranceListActivity activity;
    private ApiInterface ProviderServiceApi;
    private ApiInterface PatientServiceApi;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<ProviderInsuranceList.Item> itemListProvider;
    private List<PatientInsuranceList.Item> itemListPatient ; //null
    private InsuranceListAdapter mAdapter;
    private SwipeRefreshLayout swipeContainer;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsuranceListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        activity = this;
        setContentView(view);
        userType = getIntent().getStringExtra(ConstantsUtils.USER_TYPE);
        ProviderServiceApi  = ProviderUtils.getService();
        PatientServiceApi  = PatientUtils.getPatientService();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeInsurance);

        initView();
        swipeToRefresh();

    }

    private void swipeToRefresh() {
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(userType.equalsIgnoreCase(ConstantsUtils.provider))
                    getInsuranceDataListProvider();
                    else
                        getInsuranceDataListPatient();
                    // To keep animation for 4 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeContainer.setRefreshing(false);
                        }
                    }, 4000); // Delay in millis
                }
            });
        }

    private void initView() {
        recyclerView   = binding.recyclerViewInsuranceList;
        layoutManager = new LinearLayoutManager(activity);
        binding.imgNewInsurance.setOnClickListener(this);

        if(userType.equalsIgnoreCase(ConstantsUtils.provider))
        {
            // call method to get list of Insurance data for providers
            getInsuranceDataListProvider();
        }
        else  getInsuranceDataListPatient();

    }

    private void getInsuranceDataListPatient() {

        Call<PatientInsuranceList> call = PatientServiceApi.getInsuranceListPatient(PreferenceUtils.retriveData(this , "PUUID_PATIENT") , ConstantsUtils.API_KEY);
        call.enqueue(new Callback<PatientInsuranceList>() {
            @Override
            public void onResponse(Call<PatientInsuranceList> call, Response<PatientInsuranceList> response) {
                if(response.code() == 200)
                {

                    Toast.makeText(activity , "Patient Data" , Toast.LENGTH_SHORT).show();
                    itemListPatient = response.body().getItems();
                    mAdapter = new InsuranceListAdapter(itemListProvider, activity ,itemListPatient);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(activity , "else part "+ response.code() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PatientInsuranceList> call, Throwable t) {
                Toast.makeText(activity , "failed"  , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInsuranceDataListProvider() {
        Call<ProviderInsuranceList.ListInsurance> call = ProviderServiceApi.getInsuranceList("application/json"
                , PreferenceUtils.retriveData(this , "PUUID"), ConstantsUtils.API_KEY);
        call.enqueue(new Callback<ProviderInsuranceList.ListInsurance>() {
            @Override
            public void onResponse(Call<ProviderInsuranceList.ListInsurance> call, Response<ProviderInsuranceList.ListInsurance> response) {
                if(response.isSuccessful())
                {

                    Toast.makeText(activity , "Provider Data" , Toast.LENGTH_SHORT).show();
                    itemListProvider = response.body().getItems();
                    mAdapter = new InsuranceListAdapter(itemListProvider, activity , itemListPatient);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(activity , "else part "+ response.code() , Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<ProviderInsuranceList.ListInsurance> call, Throwable t) {
                Toast.makeText(activity , "error"+ t.getMessage()  , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_new_insurance:
                startActivity(new Intent(InsuranceListActivity.this , AddNewInsuranceActivity.class)
                .putExtra(ConstantsUtils.USER_TYPE , userType));
                break;




        }

    }
}