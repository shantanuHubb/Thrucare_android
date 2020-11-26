package com.thrucare.healthcare.activity.patient.allergies;

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
import com.thrucare.healthcare.activity.provider.AllergiesReportProviderActivity;
import com.thrucare.healthcare.databinding.FragmentAllergiesBinding;
import com.thrucare.healthcare.pojo.AllergiesInternalModel;
import com.thrucare.healthcare.pojo.PatientAllergies;
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
 * Use the {@link AllergiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllergiesFragment extends Fragment implements AllergiesAdapter.SetOnClickListenerOnAllergies  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllergiesAdapter mProductAdapter;

   List<AllergiesInternalModel> dataList  = new ArrayList();
    private ApiInterface mApiService;
    private List<PatientAllergies.Item> item;
    private SharedPreferences sharedPreferences;
    private FragmentAllergiesBinding binding;
    private int adapterPosition;
    private String value = "";
    public static AllergiesFragment allergiesFragment;

    public static AllergiesFragment newInstance(String param1, String param2) {
        AllergiesFragment fragment = new AllergiesFragment();
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
        binding = FragmentAllergiesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        allergiesFragment = this;
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sharedPreferences.contains("ForRegister")){
            value  = sharedPreferences.getString("ForRegister", "");
            if(value.equalsIgnoreCase("provider")){
                binding.llnAddAllergiesForProvider.setVisibility(View.VISIBLE);
            }else {
                binding.llnAddAllergiesForProvider.setVisibility(View.GONE);

            }

        }
        intiView(view);
    }

    public AllergiesInternalModel getItem (){
        return  dataList.get(adapterPosition);
    }
    public static  AllergiesFragment getAllergiesFragment(){
        return allergiesFragment;
    }
    private void intiView(View view) {

        recyclerView  =  view.findViewById(R.id.recyclerViewalllergies);
        layoutManager = new LinearLayoutManager(getContext());

        mApiService = ApiUtils.getApiService();
        getPatientAllergiesApiCalling();
        
        binding.imgAddAllergires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AllergiesReportProviderActivity.class)
                        .putExtra(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE  , ConstantsUtils.ALLERGIES_ADD_OR_UPDATE_ADD));
            }
        });
    }



    private void getPatientAllergiesApiCalling() {
        Call<PatientAllergies.PatientAllergiesResponse> call  = mApiService.patientAllergies();

        call.enqueue(new Callback<PatientAllergies.PatientAllergiesResponse>() {
            @Override
            public void onResponse(Call<PatientAllergies.PatientAllergiesResponse> call,
                                   Response<PatientAllergies.PatientAllergiesResponse> response) {

                if(response.code()==200){
                    PatientAllergies.PatientAllergiesResponse resObj  = response.body();
                    item  = resObj.getItems();
                    for(int i=0 ; i<item.size(); i++){
                        dataList.add(new AllergiesInternalModel(item.get(i).getReaction().get(i).getDisplay()
                                                                 ,item.get(i).getSubstance().getDisplay(),
                                                                   item.get(i).getCriticality().getDisplay(),
                                                                   item.get(i).getOnsetDate()));

                    }

                    mProductAdapter = new AllergiesAdapter(dataList, getContext() , AllergiesFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mProductAdapter);

                }
            }

            @Override
            public void onFailure(Call<PatientAllergies.PatientAllergiesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(int position) {
        adapterPosition  = position;
        if(value.equalsIgnoreCase("provider")){
            startActivity(new Intent(getContext(), AllergiesReportProviderActivity.class)
            .putExtra(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE  , ConstantsUtils.ALLERGIES_ADD_OR_UPDATE_UPDATE));
        }
    }
}