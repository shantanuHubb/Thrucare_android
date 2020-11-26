package com.thrucare.healthcare.activity.patient.vision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.order.OrderSubModuleAdapter;
import com.thrucare.healthcare.activity.patient.order.OrdersSubModuleActivity;
import com.thrucare.healthcare.activity.provider.addVision.AddNewVisionActivity;
import com.thrucare.healthcare.databinding.ActivityOrdersSubModuleBinding;
import com.thrucare.healthcare.databinding.ActivityVisionListBinding;
import com.thrucare.healthcare.pojo.VisionList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionListActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityVisionListBinding binding;
    private String userType;
    private ApiInterface mApiService;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private VisionListAdapter mVisionAdapter;
    public List<VisionList.Item> items ;
    static VisionListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisionListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        activity = this;
        // get intent data
        getDataFromIntent();

        // inti view
        intiView();

        // method for getting the vision list
        getVisionList();


    }

    public static VisionListActivity getInstance(){
        return activity;
    }

    private void getVisionList() {
        Call<VisionList.ResponseListVision> call  = mApiService.getVisionList();

        call.enqueue(new Callback<VisionList.ResponseListVision>() {
            @Override
            public void onResponse(Call<VisionList.ResponseListVision> call,
                                   Response<VisionList.ResponseListVision> response) {

                if(response.isSuccessful()){
                    items  = response.body().getItems();
                    mVisionAdapter = new VisionListAdapter(items, VisionListActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mVisionAdapter);
                    mVisionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<VisionList.ResponseListVision> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        mApiService = ApiUtils.getApiService();
        recyclerView = binding.recyclerViewVision;
        layoutManager = new LinearLayoutManager(this);
        binding.imgAddNewVision.setOnClickListener(this);

    }

    private void getDataFromIntent() {
        try {
            userType  = getIntent().getStringExtra(ConstantsUtils.For_Provider);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(userType.equalsIgnoreCase(ConstantsUtils.For_Provider)){
            binding.llnAddOrderForProvider.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.img_add_new_vision:
                startActivity(new Intent(VisionListActivity.this , AddNewVisionActivity.class));
                break;
        }
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
}