package com.thrucare.healthcare.activity.provider.providerAppointments;

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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.ConfirmAppontmentProviderActivity;
import com.thrucare.healthcare.activity.provider.PatientInfoActivity;
import com.thrucare.healthcare.activity.patient.myAppointment.MyAppointmentsModel;
import com.thrucare.healthcare.databinding.FragmentAppointmentBinding;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentFragment extends Fragment implements AppointmentAdapter.SetOnclickProviderAppointment {

    private FragmentAppointmentBinding binding;
    List<String> options=new ArrayList<String>();
    private ApiInterface mApiService;
    private AppointmentAdapter mProductAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        options.add("Today");
        options.add("Week");
        options.add("Month");
        options.add("All");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_item_spinner, options);
        binding.spinnerAppointDayFilter.setAdapter(adapter); // this will set list of values to spinner

        binding.spinnerAppointDayFilter.setSelection(options.indexOf("Today"));


        //getAppointmentDataFromProviderApiCalling();

        mApiService = ApiUtils.getApiService();
        getAppointmentDataFromProviderApiCallingRetrofit("2020-01-01", "2020-02-02");

        recyclerView  = view.findViewById(R.id.recyclerView_appointment_provider);
        layoutManager = new LinearLayoutManager(getContext());
    }

    private void getAppointmentDataFromProviderApiCallingRetrofit(String start, String end) {
        Call<MyAppointmentsModel.ResponseMyappointment> call  = mApiService.getMyAppointmentsOfProvider(start, end);

        call.enqueue(new Callback<MyAppointmentsModel.ResponseMyappointment>() {
            @Override
            public void onResponse(Call<MyAppointmentsModel.ResponseMyappointment> call,
                                   Response<MyAppointmentsModel.ResponseMyappointment> response) {
                if(response.code()==201||response.code()==200){
                    showToast("Success");

                    List<MyAppointmentsModel.Item> resObj  = response.body().getItems();

                    if(resObj.size()>0){
                        mProductAdapter = new AppointmentAdapter(resObj, AppointmentFragment.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mProductAdapter);
                        mProductAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<MyAppointmentsModel.ResponseMyappointment> call, Throwable t) {

            }
        });
    }

    private void getAppointmentDataFromProviderApiCalling() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://private-a4094-thrucare.apiary-mock.com/providers/1/appointments?start="+
                        "2020-01-01"+"&end="+"2020-02-02")

                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {


                if(response.code()==201||response.code()==200){
                    showToast("Success");

                   /* List<MyAppointmentsModel.Item> resObj  = response.body().getItems();

                    mProductAdapter = new PatientAppointmentAdapter(resObj, GalleryFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mProductAdapter);
                    mProductAdapter.notifyDataSetChanged();*/
                }


            }
        });

    }

    private void showToast(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void getPatientPosition(int getAdapterPosition,Boolean isStatusClicked) {
        if (isStatusClicked){
            startActivity(new Intent(getActivity(), ConfirmAppontmentProviderActivity.class));
        }else {
            startActivity(new Intent(getActivity(), PatientInfoActivity.class));
        }
    }
}