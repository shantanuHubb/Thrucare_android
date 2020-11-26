package com.thrucare.healthcare.activity.patient.myAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.FragmentGalleryBinding;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAppointmentFragment extends Fragment implements PatientAppointmentAdapter.SetOnClickOnSetting , CustomBottomSheet.SetOnCancelAppointment {

    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApiInterface mApiService;
    private PatientAppointmentAdapter mProductAdapter;
    private CustomBottomSheet bottomSheet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding  = FragmentGalleryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApiService  = ApiUtils.getApiService();
        // inti view
        intiView(view);
        // api calling to get my appoints data of patient
        getMyAppointmentsOfPatientApiCalling();

    }

    private void getMyAppointmentsOfPatientApiCalling() {
        Call<MyAppointmentsModel.ResponseMyappointment> call  = mApiService.getMyAppointmentsOfPatients();
        call.enqueue(new Callback<MyAppointmentsModel.ResponseMyappointment>() {
            @Override
            public void onResponse(Call<MyAppointmentsModel.ResponseMyappointment> call,
                                   Response<MyAppointmentsModel.ResponseMyappointment> response) {

                if(response.code()==200||response.code()==201){
                    showToast("Success");
                    List<MyAppointmentsModel.Item> resObj  = response.body().getItems();

                    mProductAdapter = new PatientAppointmentAdapter(resObj, MyAppointmentFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mProductAdapter);
                    mProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyAppointmentsModel.ResponseMyappointment> call, Throwable t) {

            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void intiView(View view) {
        recyclerView  =  view.findViewById(R.id.recyclerViewMyappointmentOfPatient);
        layoutManager  =  new LinearLayoutManager(getContext());

    }

    @Override
    public void openBottomSheet(int adapterPosition) {
       bottomSheet = new CustomBottomSheet( getContext(), MyAppointmentFragment.this);
        bottomSheet.show(getFragmentManager(), "Model");
    }

    @Override
    public void onCancel() {
        bottomSheet.dismiss();
    }
}