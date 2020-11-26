package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityProviderFilterBinding;
import com.thrucare.healthcare.pojo.FilterListProviderSearch;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProviderFilterBinding binding;
    private ApiInterface mApiService;

    int categoryOne  = 0;
    int categoryTwo  = 0;
    int speOne  = 0;
    int speTwo  = 0;
    int typeOne  = 0;
    int typeTwo  = 0;
    int charOne  = 0;
    int insuranceOne  = 0;
    int insuranceTwo  = 0;
    int insuranceThree  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProviderFilterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        mApiService  = ApiUtils.getApiService();
       // getFilterProviderApiCalling();
        intView();

        setRadioListener();
    }

    private void setRadioListener() {
        binding.rdGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                int selectedId = group.getCheckedRadioButtonId();
                if(findViewById(R.id.btn_rd_female).getId()== selectedId){
                    binding.btnRdMale.setChecked(false);
                    binding.btnRdFemale.setChecked(true);
                }else {
                    binding.btnRdFemale.setChecked(false);
                    binding.btnRdMale.setChecked(true);

                }
            }
        });
    }

    private void intView() {
        binding.llnCategoryMain.setOnClickListener(this);
        binding.llnCharacterMain.setOnClickListener(this);
        binding.llnDistanceMain.setOnClickListener(this);
        binding.llnGenderMain.setOnClickListener(this);
        binding.llnInsuranceMain.setOnClickListener(this);
        binding.llnRatingMain.setOnClickListener(this);
        binding.llnScheduleMain.setOnClickListener(this);
        binding.llnSpecialityMain.setOnClickListener(this);
        binding.llnTypeMain.setOnClickListener(this);
        binding.tvClaerAll.setOnClickListener(this);

        binding.llnCategorySelectOne.setOnClickListener(this);
        binding.llnCategorySelectTwo.setOnClickListener(this);

        binding.llnSpecialitySelectOne.setOnClickListener(this);
        binding.llnSpecialitySelectTwo.setOnClickListener(this);

        binding.llnTypeSelectOne.setOnClickListener(this);
        binding.llnTypeSelectTwo.setOnClickListener(this);

        binding.llnCharacterSelectOne.setOnClickListener(this);

        binding.llnInsuranceSelectOne.setOnClickListener(this);
        binding.llnInsuranceSelectTwo.setOnClickListener(this);
        binding.llnInsuranceSelectTwo.setOnClickListener(this);
        binding.tvApplyFilter.setOnClickListener(this);
    }

    private void getFilterProviderApiCalling() {
        Call<FilterListProviderSearch.ResponseFilterSearch> call = mApiService.getProviderSearchFilter();
        call.enqueue(new Callback<FilterListProviderSearch.ResponseFilterSearch>() {
            @Override
            public void onResponse(Call<FilterListProviderSearch.ResponseFilterSearch> call,
                                   Response<FilterListProviderSearch.ResponseFilterSearch> response) {
                if(response.code()==200||response.code()==201){
                    BaseUtils.showToast(ProviderFilterActivity.this, "Success");
                }
            }

            @Override
            public void onFailure(Call<FilterListProviderSearch.ResponseFilterSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lln_category_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnCategorySub , binding.imgCategory);
                break;

            case R.id.lln_speciality_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnSpecialitySub , binding.imgSpeciality);
                break;
            case R.id.lln_type_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnTypeSub , binding.imgType);
                break;

            case R.id.lln_character_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnCharacterSub , binding.imgCharacter);
                break;
            case R.id.lln_distance_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnDistanceSub , binding.imgDistance);
                break;

            case R.id.lln_schedule_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnScheduleSub , binding.imgSchedule);
                break;


            case R.id.lln_gender_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnGenderSub , binding.imgGender);
                break;

            case R.id.lln_rating_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnRatingSub , binding.imgRating);
                break;

            case R.id.lln_insurance_main:
                closeLayout();
                changeImgToPlus();
                openLayout(binding.llnInsuranceSub , binding.imgInsurance);
                break;

            case R.id.tv_claer_all:
                closeLayout();
                break;

            case R.id.lln_category_select_one:
                if(categoryOne==0){
                    binding.imgCategoryOkay1.setVisibility(View.VISIBLE);
                    categoryOne  = 1;
                }else {
                    binding.imgCategoryOkay1.setVisibility(View.GONE);
                    categoryOne  = 0;
                }

                break;

            case R.id.lln_category_select_two:

                if(categoryTwo==0){
                    binding.imgCategoryOkay2.setVisibility(View.VISIBLE);
                    categoryTwo  = 1;
                }else {
                    binding.imgCategoryOkay2.setVisibility(View.GONE);
                    categoryTwo  = 0;
                }

                break;

            case R.id.lln_speciality_select_one:
                if(speOne==0){
                    binding.imgSpecialityOkay1.setVisibility(View.VISIBLE);
                    speOne  = 1;
                }else {
                    binding.imgSpecialityOkay1.setVisibility(View.GONE);
                    speOne  = 0;
                }

                break;

            case R.id.lln_speciality_select_two:
                if(speTwo==0){
                    binding.imgSpecialityOkay2.setVisibility(View.VISIBLE);
                    speTwo  = 1;
                }else {
                    binding.imgSpecialityOkay2.setVisibility(View.GONE);
                    speTwo  = 0;
                }
                break;

            case R.id.lln_type_select_one:
                if(typeOne==0){
                    binding.imgTypeOkay1.setVisibility(View.VISIBLE);
                    typeOne  = 1;
                }else {
                    binding.imgTypeOkay1.setVisibility(View.GONE);
                    typeOne  = 0;
                }
                break;

            case R.id.lln_type_select_two:
                if(typeTwo==0){
                    binding.imgTypeOkay2.setVisibility(View.VISIBLE);
                    typeTwo  = 1;
                }else {
                    binding.imgTypeOkay2.setVisibility(View.GONE);
                    typeTwo  = 0;
                }

                break;

            case R.id.lln_character_select_one:
                if(charOne==0){
                    binding.imgCharacterOkay1.setVisibility(View.VISIBLE);
                    charOne  = 1;
                }else {
                    binding.imgCharacterOkay1.setVisibility(View.GONE);
                    charOne  = 0;
                }

                break;

            case R.id.lln_insurance_select_one:
                if(insuranceOne==0){
                    binding.imgInsuranceOkay1.setVisibility(View.VISIBLE);
                    insuranceOne  = 1;
                }else {
                    binding.imgInsuranceOkay1.setVisibility(View.GONE);
                    insuranceOne  = 0;
                }
                break;

            case R.id.lln_insurance_select_two:
                if(insuranceTwo==0){
                    binding.imgInsuranceOkay2.setVisibility(View.VISIBLE);
                    insuranceTwo  = 1;
                }else {
                    binding.imgInsuranceOkay2.setVisibility(View.GONE);
                    insuranceTwo  = 0;
                }
                break;

            case R.id.lln_insurance_select_three:
                if(insuranceThree==0){
                    binding.imgInsuranceOkay3.setVisibility(View.VISIBLE);
                    insuranceThree  = 1;
                }else {
                    binding.imgInsuranceOkay3.setVisibility(View.GONE);
                    insuranceThree  = 0;
                }
                break;

            case R.id.tv_apply_filter:
                BaseUtils.showToast(this, "Filter data saved ");
                super.onBackPressed();
                break;


        }
    }

    private void changeImgToPlus() {
        binding.imgCategory.setBackground(getDrawable(R.drawable.add32));
        binding.imgSpeciality.setBackground(getDrawable(R.drawable.add32));
        binding.imgCharacter.setBackground(getDrawable(R.drawable.add32));
        binding.imgDistance.setBackground(getDrawable(R.drawable.add32));
        binding.imgSchedule.setBackground(getDrawable(R.drawable.add32));
        binding.imgType.setBackground(getDrawable(R.drawable.add32));
        binding.imgRating.setBackground(getDrawable(R.drawable.add32));
        binding.imgInsurance.setBackground(getDrawable(R.drawable.add32));
        binding.imgGender.setBackground(getDrawable(R.drawable.add32));
    }

    private void closeLayout() {
        binding.llnCategorySub.setVisibility(View.GONE);
        binding.llnSpecialitySub.setVisibility(View.GONE);
        binding.llnTypeSub.setVisibility(View.GONE);
        binding.llnCharacterSub.setVisibility(View.GONE);
        binding.llnDistanceSub.setVisibility(View.GONE);
        binding.llnScheduleSub.setVisibility(View.GONE);
        binding.llnGenderSub.setVisibility(View.GONE);
        binding.llnRatingSub.setVisibility(View.GONE);
        binding.llnInsuranceSub.setVisibility(View.GONE);
    }

    private void openLayout(LinearLayout layout, ImageView imageView) {
        layout.setVisibility(View.VISIBLE);
        imageView.setBackground(getDrawable(R.drawable.minus32));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle(Html.fromHtml("<font color='#334D85'>Are you want to save selected options ?</font>"));
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BaseUtils.showToast(ProviderFilterActivity.this, "Filter Data Saved");
                        ProviderFilterActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ProviderFilterActivity.this.onBackPressed();
                        finish();
                    }
                });
                adb.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCancelable(true);
        adb.setTitle(Html.fromHtml("<font color='#334D85'>Are you want to save selected options ?</font>"));
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BaseUtils.showToast(ProviderFilterActivity.this, "Filter Data Saved");
                ProviderFilterActivity.this.onBackPressed();
                finish();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ProviderFilterActivity.this.onBackPressed();
                finish();
            }
        });
        adb.show();
    }
}