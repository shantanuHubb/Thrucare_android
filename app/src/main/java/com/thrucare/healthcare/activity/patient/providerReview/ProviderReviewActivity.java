package com.thrucare.healthcare.activity.patient.providerReview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thrucare.healthcare.R;

import com.thrucare.healthcare.databinding.ActivityProviderReviewBinding;
import com.thrucare.healthcare.databinding.ProviderReviewItemBinding;
import com.thrucare.healthcare.pojo.ProviderReview;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderReviewActivity extends AppCompatActivity implements BottomSheetFragment.BottomSheetListener {

    private ActivityProviderReviewBinding binding;
    private List<ProviderReview> list = new ArrayList<>();
    private ProviderReviewAdapter adapter;
    private RecyclerView recyclerView;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProviderReviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        getProviderReviews();

        binding.tvAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(),"bootomSheet");
            }
        });


        adapter = new ProviderReviewAdapter(this,list);
        initializeRecyclerView();
    }

    private void getProviderReviews() {
        mApiInterface = ApiUtils.getApiService();
        Call<ProviderReview> call = mApiInterface.getProviderReview();
        call.enqueue(new Callback<ProviderReview>() {
            @Override
            public void onResponse(Call<ProviderReview> call, Response<ProviderReview> response) {
               ProviderReview providerReviewResponse = response.body();
                list.clear();
                list.add(providerReviewResponse);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProviderReview> call, Throwable t) {

            }
        });
    }

    private void initializeRecyclerView() {
        recyclerView = binding.rcvProviderReview;
        recyclerView.setLayoutManager(new LinearLayoutManager(ProviderReviewActivity.this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.provider_review, menu);
        return true;
    }

    @Override
    public void onButtonClicked(String rating, String comment) {
        Toast.makeText(this, rating+" "+comment, Toast.LENGTH_SHORT).show();
    }
}