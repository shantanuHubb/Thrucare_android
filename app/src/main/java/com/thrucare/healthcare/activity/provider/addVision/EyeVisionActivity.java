package com.thrucare.healthcare.activity.provider.addVision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.activity.patient.vision.VisionListActivity;
import com.thrucare.healthcare.databinding.ActivityEyeVisionBinding;
import com.thrucare.healthcare.pojo.VisionList;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

public class EyeVisionActivity extends AppCompatActivity {

    private ActivityEyeVisionBinding binding;
    private String eyeType;
    private List<VisionList.Item> itemList;
    private List<VisionList.Specification> itemSpecfiction;
    private VisionList.Specification itemLeft;
    private VisionList.Specification itemRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEyeVisionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        // get intent value
        getValueOfIntent();

        getValueOfItemsAndSetOnTheView();
    }

    private void getValueOfItemsAndSetOnTheView() {
        itemList  = VisionListActivity.getInstance().items;
        Log.d("itemList" , itemList.toString());
        itemSpecfiction  = itemList.get(0).getSpecification();
        if(eyeType.equalsIgnoreCase(ConstantsUtils.LEFT)){
            try{
                itemLeft  = itemSpecfiction.get(0);
                binding.edtAdd.setText(String.valueOf(itemLeft.getAdd()));
                binding.edtAxis.setText(String.valueOf(itemLeft.getAxis()));
                binding.edtBackCurve.setText(String.valueOf(Double.valueOf(itemLeft.getBackCurve())));
                binding.edtBackDiameter.setText(String.valueOf(itemLeft.getDiameter()));
                binding.edtBrand.setText(itemLeft.getBrand());
                binding.edtColor.setText(itemLeft.getColor());
                binding.edtCylinder.setText(String.valueOf(itemLeft.getCylinder()));
                binding.edtPower.setText(String.valueOf(itemLeft.getPower()));
                binding.edtProduct.setText(itemLeft.getProduct());
                binding.edtSphere.setText(String.valueOf(itemLeft.getSphere()));
                binding.edtAmount.setText(String.valueOf(itemLeft.getPrism().getAmount()));
                binding.edtBase.setText(String.valueOf(itemLeft.getPrism().getBase()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                itemRight  = itemSpecfiction.get(0);
                binding.edtAdd.setText(String.valueOf(itemRight.getAdd()));
                binding.edtAxis.setText(String.valueOf(itemRight.getAxis()));
                binding.edtBackCurve.setText(String.valueOf(Double.valueOf(itemRight.getBackCurve())));
                binding.edtBackDiameter.setText(String.valueOf(itemRight.getDiameter()));
                binding.edtBrand.setText(itemRight.getBrand());
                binding.edtColor.setText(itemRight.getColor());
                binding.edtCylinder.setText(String.valueOf(itemRight.getCylinder()));
                binding.edtPower.setText(String.valueOf(itemRight.getPower()));
                binding.edtProduct.setText(itemRight.getProduct());
                binding.edtSphere.setText(String.valueOf(itemRight.getSphere()));
                binding.edtAmount.setText(String.valueOf(itemRight.getPrism().getAmount()));
                binding.edtBase.setText(String.valueOf(itemRight.getPrism().getBase()));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void getValueOfIntent() {
        eyeType   = getIntent().getStringExtra(ConstantsUtils.EYE_TYPE);
        if(eyeType.equalsIgnoreCase(ConstantsUtils.LEFT)){
           binding.tvHeadingVision.setText("Left");
        }else {
            binding.tvHeadingVision.setText("Right");
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}