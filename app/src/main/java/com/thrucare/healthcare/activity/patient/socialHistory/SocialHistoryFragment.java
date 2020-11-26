package com.thrucare.healthcare.activity.patient.socialHistory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.databinding.FragmentSocialHistoryBinding;
import com.thrucare.healthcare.pojo.PatientSocialHistory;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentSocialHistoryBinding binding;
    private ApiInterface mApiService;


    public SocialHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialHistoryFragment newInstance(String param1, String param2) {
        SocialHistoryFragment fragment = new SocialHistoryFragment();
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

        binding = FragmentSocialHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intiView(view);

        mApiService  = ApiUtils.getApiService();
        getSocialHistoryApiCalling();


        binding.btnSaveSocialHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.btnSaveSocialHistory.getText().toString().equalsIgnoreCase("save")){
                    saveSocialHistoryApiCalling();
                }else {
                    updateSocialHistoryApiCalling();
                }

            }
        });
    }

    private void updateSocialHistoryApiCalling() {
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("uuid", "123e4567-e89b-12d3-a456-426614174000");
        jsonObject.addProperty("occupation", binding.edtOccupation.getText().toString());
        jsonObject.addProperty("tobacco", binding.editTabacco.getText().toString());
        jsonObject.addProperty("alcohol", binding.editAlcohol.getText().toString());
        jsonObject.addProperty("recreational_drugs", binding.editRecreational.getText().toString());
        Call<ResponseBody>  call  = mApiService.updateSocialHistory(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("ResCode", String.valueOf(response.code()));
                if(response.code()==200){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Social History Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }



    private void saveSocialHistoryApiCalling() {
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("uuid", "123e4567-e89b-12d3-a456-426614174000");
        jsonObject.addProperty("occupation", binding.edtOccupation.getText().toString());
        jsonObject.addProperty("tobacco", binding.editTabacco.getText().toString());
        jsonObject.addProperty("alcohol", binding.editAlcohol.getText().toString());
        jsonObject.addProperty("recreational_drugs", binding.editRecreational.getText().toString());
        Call<ResponseBody>  call  = mApiService.saveSocialHistory(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("ResCode", String.valueOf(response.code()));
                if(response.code()==200){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Social History Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void getSocialHistoryApiCalling() {
        Call<PatientSocialHistory> call  = mApiService.getSocialHistory();
        call.enqueue(new Callback<PatientSocialHistory>() {
            @Override
            public void onResponse(Call<PatientSocialHistory> call, Response<PatientSocialHistory> response) {

                PatientSocialHistory  patientSocialHistory = response.body();

                if(patientSocialHistory.getOccupation().equalsIgnoreCase("")){
                    binding.btnSaveSocialHistory.setText("Save");
                }else {
                    binding.btnSaveSocialHistory.setText("Update");
                    binding.edtOccupation.setText(patientSocialHistory.getOccupation());
                    binding.editAlcohol.setText(patientSocialHistory.getAlcohol());
                    binding.editTabacco.setText(patientSocialHistory.getTobacco());
                    binding.editRecreational.setText(patientSocialHistory.getRecreationalDrugs());
                }

            }

            @Override
            public void onFailure(Call<PatientSocialHistory> call, Throwable t) {

            }
        });
    }

    private void intiView(View view) {

    }
}