package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesFragment;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAllergiesReportProviderBinding;
import com.thrucare.healthcare.pojo.AllergiesInternalModel;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllergiesReportProviderActivity extends AppCompatActivity
        implements AllergiesSearchAdpater.SetOnClickAllergiesSearch {

    private ActivityAllergiesReportProviderBinding binding;
    private List<String> optionsListStatus = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();

    private List<String> optionsListCriticality= new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListCriticalityModel = new ArrayList<>();

    private List<String> optionsListReactions= new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListReactionModel = new ArrayList<>();
    private ApiInterface mApisService;
    private Calendar myCalendar;
    static AllergiesReportProviderActivity activity;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private AllergiesReactionAdapter mProductAdapter;
    private List<ReactionModel> list = new ArrayList<>();
    private String valueForAddOrUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllergiesReportProviderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity  = this;
        mApisService  = ApiUtils.getApiService();

        getIntentValueAndSetValue();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        recyclerView  =  view.findViewById(R.id.recyclerViewReactionItem);
        layoutManager = new LinearLayoutManager(this);

        list.add(new ReactionModel(1, "Reaction" , ""));
        mProductAdapter = new AllergiesReactionAdapter(list,
                this , ConstantsUtils.REACTION);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
        setListeners();
        getStatusApiCalling();
        getStatusCriticalityApiCalling();
      //  getDataReactionApiCalling();
    }

    private void getIntentValueAndSetValue() {
        try{
            valueForAddOrUpdate  = getIntent().getStringExtra(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE);
             if(valueForAddOrUpdate.equalsIgnoreCase(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE_UPDATE)){
                 BaseUtils.showToast(this , "update");
                 AllergiesInternalModel itmObj  = AllergiesFragment.getAllergiesFragment().getItem();
                 binding.edtSubstanceAllergies.setText(itmObj.getSubtanceName());
                 binding.editCriticlityAllergies.setSelection(2);
                 binding.dateAllergies.setText(itmObj.getOnSetDate());
                 binding.spinnerStatusAllergies.setSelection(4);
                 binding.tvSaveAddAllergies.setVisibility(View.GONE);
                 binding.tvAddMoreAllergies.setText("Update");
                 list.add(new ReactionModel(1, "Reaction" , itmObj.getTvDocName()));
                 mProductAdapter = new AllergiesReactionAdapter(list,
                         this , ConstantsUtils.REACTION);
                 recyclerView.setLayoutManager(layoutManager);
                 recyclerView.setAdapter(mProductAdapter);
                 mProductAdapter.notifyDataSetChanged();
             }else if(valueForAddOrUpdate.equalsIgnoreCase(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE_ADD)){
                 BaseUtils.showToast(this , "add");
             }
        }catch (Exception e){

        }
    }

    public static AllergiesReportProviderActivity getInstance(){
        return activity;
    }

    private void setListeners() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        binding.dateAllergies.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(AllergiesReportProviderActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.imgPlusAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.llnReactionAllergies.getVisibility()==View.VISIBLE){
                    binding.llnReactionAllergies.setVisibility(View.GONE);


                }else if(binding.llnReasonAdd.getVisibility()==View.VISIBLE){
                    binding.llnReasonAdd.setVisibility(View.GONE);
                    binding.imgPlusAllergies.setImageDrawable(getDrawable(R.drawable.plus));
                }
                 else {
                    binding.llnReactionAllergies.setVisibility(View.VISIBLE);
                    binding.imgPlusAllergies.setImageDrawable(getDrawable(R.drawable.minus));
                    if (optionsListReactions.size() > 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllergiesReportProviderActivity.this,
                                R.layout.simple_item_spinner, optionsListReactions);
                        binding.spinnerReasonAllergires1.setAdapter(adapter); // this will set list of values to spinner

                        binding.spinnerReasonAllergires1.setSelection(optionsListReactions.indexOf(optionsListReactions.get(0)));
                    }
                }

            }
        });


        binding.imgPlusAllergiesOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llnReasonAdd.setVisibility(View.VISIBLE);
                if (optionsListReactions.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllergiesReportProviderActivity.this,
                            R.layout.simple_item_spinner, optionsListReactions);
                    binding.spinnerReasonAllergires2.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerReasonAllergires2.setSelection(optionsListReactions.indexOf(optionsListReactions.get(0)));
                }
            }
        });

        binding.tvAddMoreAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valueForAddOrUpdate.equalsIgnoreCase(ConstantsUtils.ALLERGIES_ADD_OR_UPDATE_UPDATE)){
                    if(binding.edtSubstanceAllergies.getText().toString().length()>0){
                        Toast.makeText(getApplicationContext(), "Allergies Added Successfully ", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please add the substance", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    if(binding.edtSubstanceAllergies.getText().toString().length()>0){
                        Toast.makeText(getApplicationContext(), "Allergies Added Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AllergiesReportProviderActivity.this , AllergiesReportProviderActivity.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please add the substance", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

        binding.edtSubstanceAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllergiesReportProviderActivity.this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "SUBSTANCE"));
            }
        });

        binding.tvSaveAddAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(binding.edtSubstanceAllergies.getText().toString().length()>0){
                            Toast.makeText(getApplicationContext(), "Allergies Added Successfully ", Toast.LENGTH_SHORT).show();
                            AllergiesReportProviderActivity.this.onBackPressed();
                        }else {
                            Toast.makeText(getApplicationContext(), "Please add the substance", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateAllergies.setText(sdf.format(myCalendar.getTime()));
    }
    private void getStatusApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getAllergiesCriticality();
        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListStatus.add(reObj.getItems().get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListStatus.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllergiesReportProviderActivity.this, R.layout.simple_item_spinner, optionsListStatus);
                    binding.spinnerStatusAllergies.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerStatusAllergies.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void getStatusCriticalityApiCalling() {
        Call<FinalResponseSearch.ResposeSearch> call = mApisService.getStatusAllergies();
        call.enqueue(new Callback<FinalResponseSearch.ResposeSearch>() {
            @Override
            public void onResponse(Call<FinalResponseSearch.ResposeSearch> call, Response<FinalResponseSearch.ResposeSearch> response) {
                FinalResponseSearch.ResposeSearch reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListCriticality.add(reObj.getItems().get(i).getDisplay());
                    optionsListCriticalityModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListCriticality.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllergiesReportProviderActivity.this,
                            R.layout.simple_item_spinner, optionsListCriticality);
                    binding.editCriticlityAllergies.setAdapter(adapter); // this will set list of values to spinner

                    binding.editCriticlityAllergies.setSelection(optionsListCriticality.indexOf(optionsListCriticality.get(0)));
                }

            }

            @Override
            public void onFailure(Call<FinalResponseSearch.ResposeSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getValueForSubstance(String  display) {
        binding.edtSubstanceAllergies.setText(display);
    }

    @Override
    public void getValueForReaction(String display) {
        try{
            int adpaterPosition  = PreferenceManager.getDefaultSharedPreferences(this).getInt("ADAPTER_POSITION" , 0);
            list.get(adpaterPosition).setReactionValueForAPI(display);
            mProductAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
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