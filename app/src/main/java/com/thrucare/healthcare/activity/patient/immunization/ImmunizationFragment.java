package com.thrucare.healthcare.activity.patient.immunization;

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

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.vision.VisionListActivity;
import com.thrucare.healthcare.activity.patient.vision.VisionListAdapter;
import com.thrucare.healthcare.activity.provider.addImnunization.AddNewImmunizationActivity;
import com.thrucare.healthcare.databinding.FragmentAllergiesBinding;
import com.thrucare.healthcare.databinding.FragmentImmunizationBinding;
import com.thrucare.healthcare.pojo.ImmunizationList;
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
 * Use the {@link ImmunizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImmunizationFragment extends Fragment implements View.OnClickListener ,
        ImmunizationListAdapter.SetOnClickListenerOnImmunization {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentImmunizationBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    String forProvider  = "";
    private ApiInterface mServiceApi;
    private List<ImmunizationList.Item> items;
    private ImmunizationListAdapter mImmnizationAdapter;
    private int adapterPosition;
    public static ImmunizationFragment immunizationFragment;

    public ImmunizationFragment() {
        // Required empty public constructor
    }

    public ImmunizationFragment(String for_provider) {
        this.forProvider  = for_provider;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImmunizationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImmunizationFragment newInstance(String param1, String param2) {
        ImmunizationFragment fragment = new ImmunizationFragment();
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
        binding = FragmentImmunizationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        immunizationFragment  = this;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // inti view
        intiView();

        // get call for immunization list
        getImmunizationList();
    }

    public static  ImmunizationFragment getInstance(){
        return immunizationFragment;
    }

    public ImmunizationList.Item  getItem(){
        return  items.get(adapterPosition);
    }
    private void getImmunizationList() {
        Call<ImmunizationList.ResponseListImmunization> call  = mServiceApi.getImmunizationList();
        call.enqueue(new Callback<ImmunizationList.ResponseListImmunization>() {
            @Override
            public void onResponse(Call<ImmunizationList.ResponseListImmunization> call,
                                   Response<ImmunizationList.ResponseListImmunization> response) {

                if(response.isSuccessful()){
                    items  = response.body().getItems();
                    mImmnizationAdapter = new ImmunizationListAdapter(items, ImmunizationFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mImmnizationAdapter);
                    mImmnizationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ImmunizationList.ResponseListImmunization> call, Throwable t) {

            }
        });
    }

    private void intiView() {
        recyclerView  = binding.recyclerViewImmunization;
        layoutManager  = new LinearLayoutManager(getContext());
        mServiceApi  = ApiUtils.getApiService();
        if(!forProvider.isEmpty()){
           binding.llnAddImmunizationForProvider.setVisibility(View.VISIBLE);
        }

        binding.imgAddNewImmunization.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_add_new_immunization:
                 startActivity(new Intent(getActivity() , AddNewImmunizationActivity.class)
                 .putExtra(ConstantsUtils.IMMU_ADD_OR_UPDATE  , ConstantsUtils.IMMU__ADD_OR_UPDATE_ADD));
                break;
        }
    }

    @Override
    public void onClick(int position) {
        adapterPosition  = position;
        if(!forProvider.isEmpty()){
            startActivity(new Intent(getActivity() , AddNewImmunizationActivity.class)
                    .putExtra(ConstantsUtils.IMMU_ADD_OR_UPDATE  , ConstantsUtils.IMMU_ADD_OR_UPDATE_UPDATE));
        }
    }
}