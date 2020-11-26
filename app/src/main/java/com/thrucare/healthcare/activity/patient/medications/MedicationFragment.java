package com.thrucare.healthcare.activity.patient.medications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.MedicationProviderActivity;
import com.thrucare.healthcare.databinding.FragmentMedicationBinding;
import com.thrucare.healthcare.pojo.MedicationPojp;
import com.thrucare.healthcare.pojo.PatientMedication;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicationFragment extends Fragment implements MedicationAdapter.SetOnClickOnMedicationView {


    private LinearLayoutManager layoutManager;
    private MedicationAdapter mProductAdapter;
    private RecyclerView recyclerView;
    private List<MedicationPojp> dataList  = new ArrayList();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ApiInterface mApiService;
    private List<PatientMedication.Item> item;
    private FragmentMedicationBinding binding;
    private SharedPreferences sharedPreferences;
    private String userType;
    private int adapterPosition;
    private static MedicationFragment medicationFragment;

    public MedicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicationFragment newInstance(String param1, String param2) {
        MedicationFragment fragment = new MedicationFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentMedicationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medicationFragment = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        if(sharedPreferences.contains("ForRegister")){
            userType = sharedPreferences.getString("ForRegister", "");
            if(userType.equalsIgnoreCase("provider")){
                binding.llnAddMedicationForProvider.setVisibility(View.VISIBLE);
            }else {
                binding.llnAddMedicationForProvider.setVisibility(View.GONE);

            }

        }
        intiView(view);

        binding.llnAddMedicationForProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MedicationProviderActivity.class)
                        .putExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE , ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_ADD));
            }
        });
    }

    public static MedicationFragment getInstance(){

        return medicationFragment;
    }

    public MedicationPojp getMedication(){
       return dataList.get(adapterPosition);
    }
    private void intiView(View view) {

        /*dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));
        dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));
        dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));
        dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));
        dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));
        dataList.add(new MedicationPojp("Amoxicillin", "Otitis Media" , "14/09/2020"));*/
        mApiService = ApiUtils.getApiService();
        recyclerView  =  view.findViewById(R.id.recyclerViewMedications);
        layoutManager = new LinearLayoutManager(getContext());

        getPatientMedicationApiCalling();
    }


    private void getPatientMedicationApiCalling() {
        Call<PatientMedication.PatientMedicationRes> call  = mApiService.patientMedications();

        call.enqueue(new Callback<PatientMedication.PatientMedicationRes>() {
            @Override
            public void onResponse(Call<PatientMedication.PatientMedicationRes> call,
                                   Response<PatientMedication.PatientMedicationRes> response) {

                if(response.code()==200){
                    PatientMedication.PatientMedicationRes resObj  = response.body();
                    item  = resObj.getItems();

                    if(item!=null && item.size()>0){
                        for(int i =0 ; i<item.size() ; i++) {
                            dataList.add(new MedicationPojp(item.get(i).getMedication().getDisplay(), item.get(i).getReason().get(0).getDisplay(), item.get(i).getEffectiveDate()));
                        }
                        mProductAdapter = new MedicationAdapter(dataList, getContext() , MedicationFragment.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mProductAdapter);

                    }

                }
            }

            @Override
            public void onFailure(Call<PatientMedication.PatientMedicationRes> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(int position) {
        adapterPosition  = position;
         if(userType.equalsIgnoreCase("provider")){
             startActivity(new Intent(getActivity(), MedicationProviderActivity.class)
              .putExtra(ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE , ConstantsUtils.DIAGNOSIS_ADD_OR_UPDATE_UPDATE));
         }
    }
}