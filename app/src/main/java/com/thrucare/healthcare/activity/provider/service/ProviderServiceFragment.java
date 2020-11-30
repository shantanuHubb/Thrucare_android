package com.thrucare.healthcare.activity.provider.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thrucare.healthcare.databinding.FragmentProviderServiceBinding;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class ProviderServiceFragment extends Fragment {

    private FragmentProviderServiceBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface providerServiceApi;
    private ProviderServiceAdapter mAdapter;
    private static ProviderServiceFragment instance;
    private List<ProviderServiceList.Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProviderServiceBinding.inflate(getLayoutInflater() , container , false);
        View view = binding.getRoot();
        return view.getRootView();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instance = this;
        initView();
        getServiceApiCalling();
    }


    private void initView() {
        recyclerView = binding.rvProviderService;
        layoutManager = new LinearLayoutManager(getContext());
        providerServiceApi = ProviderUtils.getService();
    }
    private void getServiceApiCalling() {

        Call<ProviderServiceList> call = providerServiceApi.getProviderServiceCall("application/json"
                , "b7e89cb8-5197-4563-8c63-39f9d5119f4d", ConstantsUtils.API_KEY);

         call.enqueue(new Callback<ProviderServiceList>() {
             @Override
             public void onResponse(Call<ProviderServiceList> call, Response<ProviderServiceList> response) {
                 if(response.code() == 200)
                 {
                     BaseUtils.showToast(getActivity() , "Success");
                     ProviderServiceList obj = response.body();
                     itemList = obj.getItems();
                     mAdapter = new ProviderServiceAdapter(itemList , getContext());
                     recyclerView.setAdapter(mAdapter);
                     recyclerView.setLayoutManager(layoutManager);
                     mAdapter.notifyDataSetChanged();
                 }else
                     BaseUtils.showToast(getActivity() , "Error Code" +response.code());
             }

             @Override
             public void onFailure(Call<ProviderServiceList> call, Throwable t) {
                 BaseUtils.showToast(getActivity() , "Failed");
             }
         });
    }

}