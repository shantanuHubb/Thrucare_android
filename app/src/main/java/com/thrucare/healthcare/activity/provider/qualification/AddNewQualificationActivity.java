package com.thrucare.healthcare.activity.provider.qualification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.databinding.ActivityAddNewQualificationBinding;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ReportCategory;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewQualificationActivity extends AppCompatActivity implements
        AllergiesReactionAdapter.SetOnClickAttachmentListener , OnClickListener{

    private ActivityAddNewQualificationBinding binding;
    private ApiInterface mApisService;
    private ApiInterface newApServices;
    private RecyclerView recyclerViewAttachment;
    List<ReactionModel> list = new ArrayList<>();
    private AllergiesReactionAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    static AddNewQualificationActivity activity;
    private int positionAttachment = 0;
    private Uri imageUri;
    private List<String> optionsListCategory = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListCategoryModel = new ArrayList<>();
    private Calendar myCalendar;
    private String startOrEnd = "";
    private int selectedPositionType  = 0;
    private int selectedPositionValue  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewQualificationBinding.inflate(getLayoutInflater());
        activity = this;
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        mApisService = ApiUtils.getApiService();
        newApServices = ProviderUtils.getService();
        initView();

        getQualificationDataApiCalling();

    }

    private void getQualificationDataApiCalling() {
        Call<ReportCategory.ResponseReportCategory> call = mApisService.getQualificationType();

        call.enqueue(new Callback<ReportCategory.ResponseReportCategory>() {
            @Override
            public void onResponse(Call<ReportCategory.ResponseReportCategory> call,
                                   Response<ReportCategory.ResponseReportCategory> response) {
                ReportCategory.ResponseReportCategory reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListCategory.add(reObj.getItems().get(i).getDisplay());
                    optionsListCategoryModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListCategory.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewQualificationActivity.this,
                            R.layout.simple_item_spinner,
                            optionsListCategory);
                    binding.spinnerTypeValueQualification.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerTypeValueQualification.setSelection(optionsListCategory.indexOf(optionsListCategory.get(0)));
                    binding.spinnerValueQualification.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerValueQualification.setSelection(optionsListCategory.indexOf(optionsListCategory.get(0)));
                }

            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static AddNewQualificationActivity getInstance() {
        return activity;
    }

    private void initView() {
        recyclerViewAttachment = binding.recylerViewAttachmentQualification;
        layoutManager = new LinearLayoutManager(this);
        list.add(new ReactionModel(1, "Attachment", ""));
        mAdapter = new AllergiesReactionAdapter(list,
                this, ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION);
        recyclerViewAttachment.setLayoutManager(layoutManager);
        recyclerViewAttachment.setAdapter(mAdapter);

        binding.btnAddNewQualSubmit.setOnClickListener(this);

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (startOrEnd.equalsIgnoreCase("start")) {
                    updateLabel();
                } else {
                    updateLabelEnd();
                }

            }

        };
        binding.tvStartDateQualification.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewQualificationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                startOrEnd = "start";

            }
        });

        binding.tvEndDateQualification.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewQualificationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                startOrEnd = "end";

            }
        });

        binding.spinnerValueQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedPositionValue  = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerTypeValueQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedPositionType  = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateLabelEnd() {
        String myFormat = "yyyy-dd-mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.tvEndDateQualification.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel() {
        String myFormat = "yyyy-dd-mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.tvStartDateQualification.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(int position) {
        positionAttachment = position;
        openGallery(position);
    }

    private void openGallery(int positionAttachment) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, positionAttachment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == positionAttachment) {
            imageUri = data.getData();
            //imageView.setImageURI(imageUri);

            Log.d("imageUri", String.valueOf(imageUri));
            BaseUtils.showToast(AddNewQualificationActivity.this, "File Uploaded Successfully");
            list.get(positionAttachment).setReactionValueForAPI("Degree cert" + positionAttachment);
            mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_new_qual_submit:
                 saveAddNewQualificationAPiCalling();
                break;
        }
    }

    private void saveAddNewQualificationAPiCalling() {


        JsonObject jsonObjMain  = new JsonObject() ;

        JsonObject jsonObjectType  = new JsonObject();
        jsonObjectType.addProperty("code"  , optionsListCategoryModel.get(selectedPositionType).getCode());
        jsonObjectType.addProperty("display"  , optionsListCategoryModel.get(selectedPositionType).getDisplayName());


        JsonObject jsonObjectValue  = new JsonObject();
        jsonObjectValue.addProperty("code" , optionsListCategoryModel.get(selectedPositionValue).getCode());
        jsonObjectValue.addProperty("display" , optionsListCategoryModel.get(selectedPositionValue).getDisplayName());



        JsonObject jsonObjectPeriod  = new JsonObject();
        jsonObjectPeriod.addProperty("start", binding.tvStartDateQualification .getText().toString() + "T00:00:00.000Z");
        jsonObjectPeriod.addProperty("end", binding.tvEndDateQualification .getText().toString()+"T00:00:00.000Z");


        jsonObjMain.add("type" , jsonObjectType);
        jsonObjMain.add("value" , jsonObjectValue);
        jsonObjMain.add("period" , jsonObjectPeriod);
        jsonObjMain.addProperty("issuer" , binding.tvIssuerNameQualification.getText().toString());

        JsonArray jsonArrayAttachment  = new JsonArray();
//        for (int i =0 ; i<list.size();i++){
//            if(!list.get(i).getReactionValueForAPI().isEmpty()){
//                JsonObject jsonObjectAtt  = new JsonObject();
//                jsonObjectAtt.addProperty("title",
//                        list.get(i).getReactionValueForAPI()  );
//                jsonArrayAttachment.add(jsonObjectAtt);
//            }
//        }
        JsonObject jsonObjectAtt  = new JsonObject();
                jsonObjectAtt.addProperty("title", "shantanu"  );
                jsonObjectAtt.addProperty("url", "https://certs/1.pdf"  );
                jsonArrayAttachment.add(jsonObjectAtt);
                jsonObjMain.add("attachments" , jsonArrayAttachment);


        Call<ProviderQualificationList.ProviderRegister> call  = newApServices.
                saveNewQualification("application/json" , jsonObjMain  , ConstantsUtils.API_KEY ,PreferenceUtils.retriveData(this , "PUUID"));

        call.enqueue(new Callback<ProviderQualificationList.ProviderRegister>() {
            @Override
            public void onResponse(Call<ProviderQualificationList.ProviderRegister> call,
                                   Response<ProviderQualificationList.ProviderRegister> response) {

                if(response.isSuccessful()){
                    BaseUtils.showToast(AddNewQualificationActivity.this , "Success Add New Qualification");
                    AddNewQualificationActivity.this.onBackPressed();
                }
                else {
                    BaseUtils.showToast(AddNewQualificationActivity.this , "message" + response.code()   //400
                    );
            }
            }

            @Override
            public void onFailure(Call<ProviderQualificationList.ProviderRegister> call, Throwable t) {
                BaseUtils.showToast(AddNewQualificationActivity.this , "failed Adding New Qualification");
            }
        });
    }
}