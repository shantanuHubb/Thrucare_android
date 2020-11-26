package com.thrucare.healthcare.activity.provider.qualification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityQualificationListBinding;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualificationListActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityQualificationListBinding binding;
    static QualificationListActivity activity;
    private ApiInterface mApisService;
    private RecyclerView recyclerView;
    private QualificationListAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<ProviderQualificationList.Item> itemList;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQualificationListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        mApisService = ProviderUtils.getService();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //mApisService = ApiUtils.getApiService();
        swipeToRefresh();
        //  inti view
        intiView();

        // call method to get list of qualification data
        //getQualificationDataApiCalling();
        getQualificationDataList();
    }

    private void swipeToRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQualificationDataList();
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

    private void getQualificationDataList() {
        Call<ProviderQualificationList.ProviderRegister> call = mApisService.getQualificationList("application/json"
                , PreferenceUtils.retriveData(this , "PUUID"), ConstantsUtils.API_KEY);
        call.enqueue(new Callback<ProviderQualificationList.ProviderRegister>() {
            @Override
            public void onResponse(Call<ProviderQualificationList.ProviderRegister> call, Response<ProviderQualificationList.ProviderRegister> response) {
                if(response.isSuccessful())
                {

                    Toast.makeText(activity , "response"+ response.code() , Toast.LENGTH_SHORT).show();
                    itemList = response.body().getItems();
                    mAdapter = new QualificationListAdapter(itemList , activity);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ProviderQualificationList.ProviderRegister> call, Throwable t) {
                Toast.makeText(activity , "error"+ t.getMessage()  , Toast.LENGTH_SHORT).show();
            }
        });

    }

    // qualification list from demo API
//    private void getQualificationDataApiCalling() {
//
//        Call<QualificationList.ResponseListQualification> call  = mApisService.getQualificationListData();
//
//        call.enqueue(new Callback<QualificationList.ResponseListQualification>() {
//            @Override
//            public void onResponse(Call<QualificationList.ResponseListQualification> call,
  //                                Response<QualificationList.ResponseListQualification> response) {
//
//                if(response.isSuccessful()){
//                    List<QualificationList.Item> itemList  = response.body().getItems();
//                    mAdapter = new QualificationListAdapter(itemList,
//                            QualificationListActivity.this);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<QualificationList.ResponseListQualification> call, Throwable t) {
//               t.printStackTrace();
//            }
//        });
//
//    }

    private void intiView() {
        recyclerView   = binding.recyclerViewQualificationList;
        layoutManager = new LinearLayoutManager(activity);
        binding.imgNewQualification.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_new_qualification:
                  startActivity(new Intent(QualificationListActivity.this , AddNewQualificationActivity.class));
                break;

        }
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