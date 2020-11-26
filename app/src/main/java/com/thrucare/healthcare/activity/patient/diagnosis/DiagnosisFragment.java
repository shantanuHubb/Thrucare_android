package com.thrucare.healthcare.activity.patient.diagnosis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddDiagnosisActivity;
import com.thrucare.healthcare.databinding.FragmentDiagnosisBinding;
import com.thrucare.healthcare.pojo.MedicationPojp;
import com.thrucare.healthcare.pojo.PatientDiagnosis;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiagnosisFragment extends Fragment implements DiagnosisAdapter.SetOnCardClickListener {

    private SlideshowViewModel slideshowViewModel;
    private LinearLayoutManager layoutManager;
    private DiagnosisAdapter mProductAdapter;
    private RecyclerView recyclerView;
    private List<MedicationPojp> dataList  = new ArrayList();
    private ApiInterface mApiService;
    private List<PatientDiagnosis.Item> item;
    private SharedPreferences sharedPreferences;
    private FragmentDiagnosisBinding binding;
    private String userType;
    private int adapterPosition;
    private static DiagnosisFragment diagnosisFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiagnosisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         diagnosisFragment  = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        if(sharedPreferences.contains("ForRegister")){
          userType = sharedPreferences.getString("ForRegister", "");
            if(userType.equalsIgnoreCase("provider")){
               binding.llnAddDiagnosisForProvider.setVisibility(View.VISIBLE);
            }else {
                binding.llnAddDiagnosisForProvider.setVisibility(View.GONE);

            }

        }
        intiView(view);


        binding.llnAddDiagnosisForProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getContext(), AddDiagnosisActivity.class)
                      .putExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE , ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_ADD));
            }
        });
    }

    private void intiView(View view) {

        mApiService = ApiUtils.getApiService();
        recyclerView  =  view.findViewById(R.id.recyclerViewDiagnosis);
        layoutManager = new LinearLayoutManager(getContext());
        getPatientDiagnosisApiCalling();
    }


    public static DiagnosisFragment getDiagnosisFragment(){
        return diagnosisFragment;
    }
    public MedicationPojp  getDataForUpdate(){
       return dataList.get(adapterPosition);
    }
    private void getPatientDiagnosisApiCalling() {
        Call<PatientDiagnosis.PatientDiagnosisResponse> call  = mApiService.patientDiagnosis();

        call.enqueue(new Callback<PatientDiagnosis.PatientDiagnosisResponse>() {
            @Override
            public void onResponse(Call<PatientDiagnosis.PatientDiagnosisResponse> call, Response<PatientDiagnosis.PatientDiagnosisResponse> response) {

                if(response.code()==200){
                    PatientDiagnosis.PatientDiagnosisResponse resObj  = response.body();
                      item  = resObj.getItems();

                      if(item!=null && item.size()>0){
                          for(int i =0 ; i<item.size() ; i++) {
                              dataList.add(new MedicationPojp(item.get(i).getProblem().getDisplay(), item.get(i).getSeverity().getDisplay(), item.get(i).getOnsetDate()));
                          }


                          mProductAdapter = new DiagnosisAdapter(dataList, getContext() , DiagnosisFragment.this);
                          recyclerView.setLayoutManager(layoutManager);
                          recyclerView.setAdapter(mProductAdapter);

                      }

                }
            }

            @Override
            public void onFailure(Call<PatientDiagnosis.PatientDiagnosisResponse> call, Throwable t) {
               t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(int position) {
        adapterPosition  = position;
        if(userType.equalsIgnoreCase("provider")){
            startActivity(new Intent(getContext(), AddDiagnosisActivity.class)
                    .putExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE , ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_UPDATE));
        }
    }
}