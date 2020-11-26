package com.thrucare.healthcare.activity.patient.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.bookAppointment.CurrentLocationActivity;
import com.thrucare.healthcare.activity.patient.bookAppointment.LocationScreenActivity;
import com.thrucare.healthcare.activity.patient.bookAppointment.ProviderFilterActivity;
import com.thrucare.healthcare.databinding.FragmentHomeBinding;
import com.thrucare.healthcare.activity.patient.bookAppointment.MapsActivity;
import com.thrucare.healthcare.pojo.FilterListProviderSearch;
import com.thrucare.healthcare.pojo.OrganizationsType;
import com.thrucare.healthcare.pojo.OriganzationTypeInternalModel;
import com.thrucare.healthcare.pojo.OrigazationSearch;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener ,
        HomeOrganizationAdapter.SetOnClickOrganzation , CurrentLocationActivity.AutoDetectListener,
        MapsActivity.SearchLocationListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ApiInterface mApiService;
    private LinearLayoutManager layoutManager;
    private HomeOrganizationAdapter mProductAdapter;
    private RecyclerView recyclerViewProviderList;
    private LinearLayoutManager layoutManagerProviderList;
    private HomeProviderListAdapter mProviderListAdapter;
    private List<OriganzationTypeInternalModel> list;
    public static HomeFragment instance ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // inti view
        intiView(view);
        instance = this;

    }

    public static HomeFragment getInstance(){
        return instance;
    }
    private void intiView(View view) {
        binding.imgSelectFilterProvider.setOnClickListener(this);
        mApiService = ApiUtils.getApiService();
        recyclerView  =  view.findViewById(R.id.recyclerView_organizations_type);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);



        recyclerViewProviderList  =  view.findViewById(R.id.recyclerView_provider_list);
        layoutManagerProviderList = new LinearLayoutManager(getContext());

        getOriganzationTypeApiCalling();

        view.findViewById(R.id.img_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getActivity(), MapsActivity.class));
               // startActivity(new Intent(getActivity(), LocationScreenActivity.class));

            }
        });
    }

    private void getOriganzationTypeApiCalling() {
        Call<OrganizationsType.ResponseOrganizationType> call  = mApiService.getOrganizationsTypes();

        call.enqueue(new Callback<OrganizationsType.ResponseOrganizationType>() {
            @Override
            public void onResponse(Call<OrganizationsType.ResponseOrganizationType> call,
                                   Response<OrganizationsType.ResponseOrganizationType> response) {

                if(response.code()==200||response.code()==201){
                    List<OrganizationsType.Item> resObjList  = response.body().getItems();
                     list  = new ArrayList<>();
                    for(int i =0 ; i<resObjList.size(); i++){

                        if(i==1){
                            list.add(new OriganzationTypeInternalModel(resObjList.get(i).getCode(), resObjList.get(i).getValue(),
                                    1));
                        }else {
                            list.add(new OriganzationTypeInternalModel(resObjList.get(i).getCode(), resObjList.get(i).getValue(),
                                    0));
                        }


                    }
                    if(list.size()>0){
                        mProductAdapter = new HomeOrganizationAdapter(list, HomeFragment.this , getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mProductAdapter);

                        List<OrigazationSearch.ResponseOrganizationSearch> list  = new ArrayList<>();
                        mProviderListAdapter = new HomeProviderListAdapter(list, getContext() , 0);
                        recyclerViewProviderList.setLayoutManager(layoutManagerProviderList);
                        recyclerViewProviderList.setAdapter(mProviderListAdapter);
                        mProductAdapter.notifyDataSetChanged();


                    }

                }
            }

            @Override
            public void onFailure(Call<OrganizationsType.ResponseOrganizationType> call, Throwable t) {
                  t.printStackTrace();
            }
        });
    }
    private void getFilterProviderApiCalling() {
        Call<FilterListProviderSearch.ResponseFilterSearch> call = mApiService.getProviderSearchFilter();
        call.enqueue(new Callback<FilterListProviderSearch.ResponseFilterSearch>() {
            @Override
            public void onResponse(Call<FilterListProviderSearch.ResponseFilterSearch> call,
                                   Response<FilterListProviderSearch.ResponseFilterSearch> response) {
                if(response.code()==200||response.code()==201){
                    BaseUtils.showToast(getActivity(), "Success");

                    String providerName  = response.body().getResults().get(0).getHits().get(0).getFirstName()+
                            " "+response.body().getResults().get(0).getHits().get(0).getFirstName();

                    String type  = response.body().getResults().get(0).getHits().get(0)
                            .getMetadata().getServices().get(0).getType().get(0);
                    String type1  = response.body().getResults().get(0).getHits().get(0)
                            .getMetadata().getServices().get(0).getType().get(1);

                    String speciality  = response.body().getResults().get(0).getHits().get(0)
                            .getMetadata().getServices().get(0).getSpeciality().get(0);

                    String value = "Multispeciality Dental Clinic";



                }
            }

            @Override
            public void onFailure(Call<FilterListProviderSearch.ResponseFilterSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void origanazationSearchApiCalling(String  code) {
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("location", "Delhi");

        JsonObject jsonObjectSelectedType  = new JsonObject();
        jsonObjectSelectedType.addProperty("selectedType", "Pharmacy");
        jsonObject.add("type", jsonObjectSelectedType);

        jsonObject.addProperty("distance", "10");
        jsonObject.addProperty("rating", "3");

        JsonArray jsonArrayInsurance  = new JsonArray();
        jsonArrayInsurance.add("BCBS");
        jsonObject.add("insurance" , jsonArrayInsurance);
        jsonObject.addProperty("terms", "txt");

        Call<OrigazationSearch.ResponseOrganizationSearch> call = mApiService.getOrganizationsSearch();

        call.enqueue(new Callback<OrigazationSearch.ResponseOrganizationSearch>() {
            @Override
            public void onResponse(Call<OrigazationSearch.ResponseOrganizationSearch> call,
                                   Response<OrigazationSearch.ResponseOrganizationSearch> response) {

                if(response.code()==200||response.code()==201){
                    BaseUtils.showToast(getActivity(), "Origanzation Search");
                    OrigazationSearch.ResponseOrganizationSearch  resObj  = response.body();

                    List<OrigazationSearch.ResponseOrganizationSearch> list  = new ArrayList<>();
                    list.add(resObj);
                    mProviderListAdapter = new HomeProviderListAdapter(list, getContext() , 1);
                    recyclerViewProviderList.setLayoutManager(layoutManagerProviderList);
                    recyclerViewProviderList.setAdapter(mProviderListAdapter);
                    mProductAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<OrigazationSearch.ResponseOrganizationSearch> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_select_filter_provider:
                 startActivity(new Intent(getContext(), ProviderFilterActivity.class));

                break;
        }

    }

    @Override
    public void onClick(int adapterPostion) {
        if(adapterPostion!=1){
            origanazationSearchApiCalling(list.get(0).code);
        }else {
            List<OrigazationSearch.ResponseOrganizationSearch> list  = new ArrayList<>();
            mProviderListAdapter = new HomeProviderListAdapter(list, getContext() , 0);
            recyclerViewProviderList.setLayoutManager(layoutManagerProviderList);
            recyclerViewProviderList.setAdapter(mProviderListAdapter);
            mProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(String location , double lat, double langt) {
        binding.tvLocationUser.setText(location);
    }

    @Override
    public void onClickSearch(String location, double lat, double longt) {
        binding.tvLocationUser.setText(location);
    }
}