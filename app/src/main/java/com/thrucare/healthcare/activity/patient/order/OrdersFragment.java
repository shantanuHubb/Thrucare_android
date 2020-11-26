package com.thrucare.healthcare.activity.patient.order;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.deviceOrder.DeviceOrderActivity;
import com.thrucare.healthcare.activity.patient.vision.VisionListActivity;
import com.thrucare.healthcare.databinding.FragmentHomeBinding;
import com.thrucare.healthcare.databinding.FragmentOrdersBinding;
import com.thrucare.healthcare.utils.ConstantsUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentOrdersBinding binding;
    String forProvider = "";
    public OrdersFragment() {
        // Required empty public constructor
    }
    public OrdersFragment(String value) {
        this.forProvider  = value;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        binding = FragmentOrdersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intiView();
    }

    private void intiView() {
        binding.cdMedicationOrder.setOnClickListener(this);
        binding.cdLabOrder.setOnClickListener(this);
        binding.cdRefferalOrder.setOnClickListener(this);
        binding.cdVisionOrder.setOnClickListener(this);
        binding.cdRadiologyOrder.setOnClickListener(this);
        binding.cdDeviceOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cd_medication_order:
                Log.d("order" , forProvider);
                startActivity(new Intent(getContext() , OrdersSubModuleActivity.class)
                        .putExtra(ConstantsUtils.SUB_LAYOUT_ORDER_TYPE , ConstantsUtils.MEDICATION_ORDER )
                        .putExtra(ConstantsUtils.For_Provider , forProvider));

                break;

            case R.id.cd_lab_order:
                startActivity(new Intent(getContext() , OrdersSubModuleActivity.class)
                        .putExtra(ConstantsUtils.SUB_LAYOUT_ORDER_TYPE , ConstantsUtils.LAB_ORDER )
                         .putExtra(ConstantsUtils.For_Provider , forProvider));

                break;


            case R.id.cd_refferal_order:
                startActivity(new Intent(getContext() , OrdersSubModuleActivity.class)
                        .putExtra(ConstantsUtils.SUB_LAYOUT_ORDER_TYPE , ConstantsUtils.REFFERAL_ORDER )
                        .putExtra(ConstantsUtils.For_Provider , forProvider));
                break;


            case R.id.cd_vision_order:
                startActivity(new Intent(getContext() , VisionListActivity.class)
                        .putExtra(ConstantsUtils.For_Provider , forProvider));
                break;

            case R.id.cd_radiology_order:
                startActivity(new Intent(getContext() , OrdersSubModuleActivity.class)
                        .putExtra(ConstantsUtils.SUB_LAYOUT_ORDER_TYPE , ConstantsUtils.RADIOLOGY_ORDER )
                        .putExtra(ConstantsUtils.For_Provider , forProvider));
                break;
            case R.id.cd_device_order:
                 startActivity(new Intent(getContext(), DeviceOrderActivity.class)
                         .putExtra(ConstantsUtils.For_Provider , forProvider));
                break;



        }
    }

}