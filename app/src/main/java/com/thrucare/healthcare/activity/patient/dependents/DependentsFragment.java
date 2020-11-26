package com.thrucare.healthcare.activity.patient.dependents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesAdapter;
import com.thrucare.healthcare.databinding.FragmentAllergiesBinding;
import com.thrucare.healthcare.databinding.FragmentDependentsBinding;
import com.thrucare.healthcare.pojo.DependentsList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DependentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DependentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentDependentsBinding binding;
    private RecyclerView recyclerViewDependents;
    private LinearLayoutManager layoutManager;
    private ApiInterface mApiService;
    private DependentsAdapter mDependentsAdapter;

    public DependentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DependentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DependentsFragment newInstance(String param1, String param2) {
        DependentsFragment fragment = new DependentsFragment();
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
        binding = FragmentDependentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // inti view
        intiView();

        // method to get List of dependents
        getDependentsListApiCalling();
    }

    private void getDependentsListApiCalling() {

        Call<DependentsList.ResponseListDependents> call  = mApiService.getListOfPatientDependents();

        call.enqueue(new Callback<DependentsList.ResponseListDependents>() {
            @Override
            public void onResponse(Call<DependentsList.ResponseListDependents> call,
                                   Response<DependentsList.ResponseListDependents> response) {

                if(response.isSuccessful()){
                    List<DependentsList.Item> itemList  = response.body().getItems();
                    mDependentsAdapter = new DependentsAdapter(itemList, getContext());
                    recyclerViewDependents.setLayoutManager(layoutManager);
                    recyclerViewDependents.setAdapter(mDependentsAdapter);
                    mDependentsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DependentsList.ResponseListDependents> call, Throwable t) {

            }
        });

    }

    private void intiView() {
        recyclerViewDependents  = binding.recyclerViewDependents ;
        layoutManager  = new LinearLayoutManager(getContext());
        mApiService   = ApiUtils.getApiService();

    }
}