package com.thrucare.healthcare.activity.provider.quotation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesAdapter;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesFragment;
import com.thrucare.healthcare.activity.provider.qualification.QualificationListActivity;
import com.thrucare.healthcare.databinding.ActivityAddDiagnosisBinding;
import com.thrucare.healthcare.databinding.ActivityQuotationList2Binding;
import com.thrucare.healthcare.pojo.ResponseQuotation;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationListActivity extends AppCompatActivity {

    private ActivityQuotationList2Binding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface apiService;
    private QuotationListAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuotationList2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        // inti view
        intiView();


        //  method to get
        getQuotationListApiCalling();
    }

    private void getQuotationListApiCalling() {
        Call<ResponseQuotation.ResponseQuotationList> call   = apiService.getQuotationList();

        call.enqueue(new Callback<ResponseQuotation.ResponseQuotationList>() {
            @Override
            public void onResponse(Call<ResponseQuotation.ResponseQuotationList> call,
                                   Response<ResponseQuotation.ResponseQuotationList> response) {

                if(response.isSuccessful()){
                    if(response.code()==200){
                        BaseUtils.showToast(QuotationListActivity.this , "success");
                       List<ResponseQuotation.Item> itemList  = response.body().getItems();
                        mProductAdapter = new QuotationListAdapter(itemList, QuotationListActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mProductAdapter);
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseQuotation.ResponseQuotationList> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        recyclerView  =  binding.recyclerViewQuotation;
         layoutManager = new LinearLayoutManager(this);
         apiService  = ApiUtils.getApiService();

    }
}