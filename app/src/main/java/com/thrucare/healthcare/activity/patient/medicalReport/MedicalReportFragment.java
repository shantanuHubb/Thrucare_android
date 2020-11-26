package com.thrucare.healthcare.activity.patient.medicalReport;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.medications.MedicationAdapter;
import com.thrucare.healthcare.activity.provider.AddMedicalReportActivity;
import com.thrucare.healthcare.databinding.ActivityAddDiagnosisBinding;
import com.thrucare.healthcare.databinding.FragmentMedicalReportragmentBinding;
import com.thrucare.healthcare.pojo.MedicalReportList;
import com.thrucare.healthcare.pojo.MedicationPojp;
import com.thrucare.healthcare.pojo.PatientMedication;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalReportFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentMedicalReportragmentBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface mApiService;
    private MedicalReportAdapter mAdapter;

    public MedicalReportFragment() {
        // Required empty public constructor
    }
  String forProvider = "";
    public MedicalReportFragment(String for_provider) {
        this.forProvider  =  for_provider;
    }

    public static MedicalReportFragment newInstance(String param1, String param2) {
        MedicalReportFragment fragment = new MedicalReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        binding = FragmentMedicalReportragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        //  inti view
        intiView();
        
        getMedicalReportDataApiCalling();
    }

    private void getMedicalReportDataApiCalling() {
        Call<MedicalReportList.ResponseMedicalReport> call  = mApiService.getMedicalReport();
        call.enqueue(new Callback<MedicalReportList.ResponseMedicalReport>() {
            @Override
            public void onResponse(Call<MedicalReportList.ResponseMedicalReport> call,
                                   Response<MedicalReportList.ResponseMedicalReport> response) {

                if (response.isSuccessful()) {
                    BaseUtils.showToast(getActivity(), "Success from Medical Report");
                    List<MedicalReportList.Item> resObjList  = response.body().getItems();
                    mAdapter = new MedicalReportAdapter(resObjList, getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(Call<MedicalReportList.ResponseMedicalReport> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        mApiService = ApiUtils.getApiService();
        recyclerView   = binding.recyclerViewMedicalReport;
        layoutManager = new LinearLayoutManager(getContext());
        binding.llnAddMedicalReportForProvider.setOnClickListener(this);
        if(forProvider.equalsIgnoreCase(ConstantsUtils.For_Provider)){
            binding.llnAddMedicalReportForProvider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lln_add_medical_report_for_provider:
                startActivity(new Intent(getActivity() , AddMedicalReportActivity.class));
                break;
        }
    }

}