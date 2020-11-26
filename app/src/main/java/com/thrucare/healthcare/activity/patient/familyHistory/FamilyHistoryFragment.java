package com.thrucare.healthcare.activity.patient.familyHistory;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddFamilyHistoryActivity;
import com.thrucare.healthcare.pojo.FamilyHistory;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyHistoryFragment extends Fragment implements FamilyHistoryAdapter.SetOnClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface mApiService;
    private List<FamilyHistory.Item> item;
    private List<FamilyHistory.Item> dataList;
    private FamilyHistoryAdapter mProductAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family_history, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intiView(view);
    }

    private void intiView(View view) {
        recyclerView  =  view.findViewById(R.id.recyclerViewFamilyHistory);
        layoutManager = new LinearLayoutManager(getContext());
        mApiService = ApiUtils.getApiService();
        getPatientFamilyHistoryApiCalling();

        view.findViewById(R.id.img_Add_family_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddFamilyHistoryActivity.class));
            }
        });


    }

    private void getPatientFamilyHistoryApiCalling() {
        Call<FamilyHistory.PatientFamilyHistory> call  = mApiService.patientFamilyHistory();

        call.enqueue(new Callback<FamilyHistory.PatientFamilyHistory>() {
            @Override
            public void onResponse(Call<FamilyHistory.PatientFamilyHistory> call,
                                   Response<FamilyHistory.PatientFamilyHistory> response) {

                if(response.code()==200){
                    FamilyHistory.PatientFamilyHistory resObj  = response.body();
                    item  = resObj.getItems();

                    mProductAdapter = new FamilyHistoryAdapter(item,  FamilyHistoryFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mProductAdapter);

                }
            }

            @Override
            public void onFailure(Call<FamilyHistory.PatientFamilyHistory> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClickOnDeleteFamilyHistory(int position) {


        Call<ResponseBody> call   = mApiService.deleteFamilyHistory();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code()==204){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}