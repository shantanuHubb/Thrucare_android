package com.thrucare.healthcare.activity.provider.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.databinding.ActivityAddNewInsuranceBinding;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ProductInsuranceModel;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ReportCategory;
import com.thrucare.healthcare.pojo.modelClasses.PatientAddInsurance;
import com.thrucare.healthcare.pojo.modelClasses.ProviderAddInsurance;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewInsuranceActivity extends AppCompatActivity implements AddProductAdapter.SetOnClickProductListener ,
        AllergiesReactionAdapter.SetOnClickAttachmentListener , View.OnClickListener{

    private ActivityAddNewInsuranceBinding binding;
    static AddNewInsuranceActivity activity ;
    private ApiInterface mApisService;
    private ApiInterface providerApiService;
    private ApiInterface patientApiService;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<ProductInsuranceModel> list = new ArrayList<>();
    private List<String> listSpinner   = new ArrayList<>();
    private AddProductAdapter mProductAdapter;
    private List<ModelCodeAndDisplay> optionsListInsuranceModel = new ArrayList<>();
    private ArrayList<ModelCodeAndDisplay> optionsListPayerModel  = new ArrayList<>();
    private List<String> optionsListPayer = new ArrayList<>();
    private List<ReactionModel> listAttachment  = new ArrayList<>();
    private AllergiesReactionAdapter mAdapter;
    private RecyclerView recyclerViewAttachment;
    private LinearLayoutManager layoutManager1;
    private int positionAttachment;
    private Uri imageUri;
    private int selectedPostionPayer = 0;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewInsuranceBinding.inflate(getLayoutInflater());
        activity = this;
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        //adding payer and product on the name font before selection from list
        optionsListPayer.add(0 , " Payer");
        optionsListPayerModel.add(new ModelCodeAndDisplay("0" , "Payer"));
        //product add element at index 1
        listSpinner.add(0 , "Product");
        optionsListInsuranceModel.add(new ModelCodeAndDisplay("0" , "Product"));

        mApisService = ApiUtils.getApiService();
        providerApiService = ProviderUtils.getService();
        patientApiService = PatientUtils.getPatientService();
        userType  =  getIntent().getStringExtra(ConstantsUtils.USER_TYPE);
        Log.d("AddNewInsuranceActivity", "onCreate: " + userType);
        getProductInsurancePayerApiCalling();
        getProductListingApiCalling();
        intiView();
    }

    public  static AddNewInsuranceActivity getInstance(){
        return activity;
    }
    private void getProductInsurancePayerApiCalling() {
        Call<ReportCategory.ResponseReportCategory> call = mApisService.getProductInsurancePayer();

        call.enqueue(new Callback<ReportCategory.ResponseReportCategory>() {
            @Override
            public void onResponse(Call<ReportCategory.ResponseReportCategory> call,
                                   Response<ReportCategory.ResponseReportCategory> response) {

                ReportCategory.ResponseReportCategory reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListPayer.add(reObj.getItems().get(i).getDisplay());

                    optionsListPayerModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListPayer.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewInsuranceActivity.this,
                            R.layout.simple_item_spinner, optionsListPayer);
                    binding.spinnerPayerInsurance.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerPayerInsurance.setSelection(optionsListPayer.indexOf(optionsListPayer.get(0)));
                }
            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {

            }
        });
    }

    private void getProductListingApiCalling() {
        Call<ReportCategory.ResponseReportCategory> call  = mApisService.getProductListingInsurance();

        call.enqueue(new Callback<ReportCategory.ResponseReportCategory>() {
            @Override
            public void onResponse(Call<ReportCategory.ResponseReportCategory> call,
                                   Response<ReportCategory.ResponseReportCategory> response) {
                ReportCategory.ResponseReportCategory reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    listSpinner.add(reObj.getItems().get(i).getDisplay());
                    optionsListInsuranceModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }

                if(listSpinner.size()>0){
                    mProductAdapter = new AddProductAdapter(list,
                            AddNewInsuranceActivity.this , listSpinner , optionsListInsuranceModel);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mProductAdapter);
                    mProductAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {

            }
        });
    }

    private void intiView() {
       recyclerView  =  binding.recyclerViewProductInsurance ;
       layoutManager  = new LinearLayoutManager(this);
        list.add(new ProductInsuranceModel(1,  ""  , 0 , ""));


        if(userType.equalsIgnoreCase(ConstantsUtils.patient)){
            recyclerViewAttachment  =  binding.recylerViewAttachmentInsurance ;
            layoutManager1  = new LinearLayoutManager(this);
            listAttachment.add(new ReactionModel(1, "Attachment", ""));
            mAdapter = new AllergiesReactionAdapter(listAttachment,
                    this, ConstantsUtils.ADD_ATTACHMENT_INSURANCE);
            recyclerViewAttachment.setLayoutManager(layoutManager1);
            recyclerViewAttachment.setAdapter(mAdapter);
        }else {
            binding.tvProductInsurance.setVisibility(View.GONE);
            binding.recylerViewAttachmentInsurance.setVisibility(View.GONE);
        }


        binding.tvAddMoreInsurance.setOnClickListener(this);
        binding.tvSaveAddInsurance.setOnClickListener(this);

        binding.spinnerPayerInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPostion, long l) {


                selectedPostionPayer  = selectedPostion;
              //  Log.d("selectItem", "onItemSelected: select item" + optionsListPayerModel.get(selectedPostionPayer).getDisplayName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
    public void getListOfProduct(List<ProductInsuranceModel> list) {
    this.list = list;
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
            BaseUtils.showToast(AddNewInsuranceActivity.this, "File Uploaded Successfully");
            listAttachment.get(positionAttachment).setReactionValueForAPI("Degree cert" + positionAttachment);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_add_more_insurance:
                //saveInsuranceApiCalling();
                saveInsuranceCallProvider();
                Intent mIntent = getIntent();
                finish();
                startActivity(mIntent);

                break;


            case R.id.tv_save_add_insurance:
                //saveInsuranceApiCalling();
                if(userType.equalsIgnoreCase(ConstantsUtils.patient))
                {
                    saveInsuranceCallPatient();
                }else saveInsuranceCallProvider();
                this.onBackPressed();
                break;
        }
    }

    private void saveInsuranceCallPatient() {
        JsonObject jsonMainObject = new JsonObject();

        JsonObject jsonObjectPayer  = new JsonObject() ;

        jsonObjectPayer.addProperty("code" , optionsListPayerModel.get(selectedPostionPayer).getCode());
        jsonObjectPayer.addProperty("display" , optionsListPayerModel.get(selectedPostionPayer).getDisplayName());

        JsonArray jsonArray  = new JsonArray();
        for(int i =0 ; i< list.size() ; i++){
            JsonObject jsonObject  = new JsonObject();
            jsonObject.addProperty("code" , list.get(i).getSelectedCode());
            jsonObject.addProperty("display" , list.get(i).getSelectedDisplay());
            jsonArray.add(jsonObject);
            jsonObjectPayer.add("product", jsonArray);
        }

        JsonArray jsonArrayAttachment = new JsonArray();
        JsonObject jsonObjectAttachment = new JsonObject();
        jsonObjectAttachment.addProperty("title" , "Insurrance card");
        jsonObjectAttachment.addProperty("url" , "https://cards/1.pdf");
        jsonArrayAttachment.add(jsonObjectAttachment);

        jsonObjectPayer.add("attachments" ,jsonArrayAttachment );
        jsonMainObject.add("payer" , jsonObjectPayer);

        Call<PatientAddInsurance> call  = patientApiService.saveNewInsurancePatient(
                "application/json" , jsonMainObject ,PreferenceUtils.retriveData(this , "PUUID_PATIENT"),  ConstantsUtils.API_KEY );

        call.enqueue(new Callback<PatientAddInsurance>() {
            @Override
            public void onResponse(Call<PatientAddInsurance> call, Response<PatientAddInsurance> response) {
                if(response.isSuccessful()){
                    BaseUtils.showToast(AddNewInsuranceActivity.this, "Success Adding Patient insurance");
                }
                else
                    BaseUtils.showToast(AddNewInsuranceActivity.this, "else part " + response.code());
            }

            @Override
            public void onFailure(Call<PatientAddInsurance> call, Throwable t) {
                BaseUtils.showToast(AddNewInsuranceActivity.this, "Failed ");
            }
        });
    }

    private void saveInsuranceCallProvider() {
        JsonObject jsonMainObject = new JsonObject();

        JsonObject jsonObjectPayer  = new JsonObject() ;

        jsonObjectPayer.addProperty("code" , optionsListPayerModel.get(selectedPostionPayer).getCode());
        jsonObjectPayer.addProperty("display" , optionsListPayerModel.get(selectedPostionPayer).getDisplayName());

        JsonArray jsonArray  = new JsonArray();
        for(int i =0 ; i< list.size() ; i++){
            JsonObject jsonObject  = new JsonObject();
            jsonObject.addProperty("code" , list.get(i).getSelectedCode());
            jsonObject.addProperty("display" , list.get(i).getSelectedDisplay());
            jsonArray.add(jsonObject);
            jsonObjectPayer.add("product", jsonArray);
        }



        jsonMainObject.add("payer" , jsonObjectPayer);

        Call<ProviderAddInsurance.AddInsuranceRes> call  = providerApiService.saveNewInsurance(
                "application/json" , jsonMainObject ,PreferenceUtils.retriveData(this , "PUUID"),  ConstantsUtils.API_KEY );

        call.enqueue(new Callback<ProviderAddInsurance.AddInsuranceRes>() {
            @Override
            public void onResponse(Call<ProviderAddInsurance.AddInsuranceRes> call,
                                   Response<ProviderAddInsurance.AddInsuranceRes> response) {
                if(response.isSuccessful()){
                    BaseUtils.showToast(AddNewInsuranceActivity.this, "Success  Adding Provider insurance");
                }
                else
                    BaseUtils.showToast(AddNewInsuranceActivity.this, "else part " + response.code());
            }

            @Override
            public void onFailure(Call<ProviderAddInsurance.AddInsuranceRes> call, Throwable t) {
                BaseUtils.showToast(AddNewInsuranceActivity.this, "failed");
            }
        });
    }

//    private void saveInsuranceApiCalling() {
//        JsonObject jsonObjectMain  = new JsonObject() ;
//
//        jsonObjectMain.addProperty("code" , optionsListPayerModel.get(selectedPostionPayer).getCode());
//        JsonArray jsonArray  = new JsonArray();
//        for(int i =0 ; i< list.size() ; i++){
//            JsonObject jsonObject  = new JsonObject();
//            jsonObject.addProperty("code" , list.get(i).getSelectedCode());
//            jsonArray.add(jsonObject);
//        }
//        jsonObjectMain.add("product", jsonArray);
//       Call<AddInsurance.ResponseAddInsurance> call  = mApisService.saveNewInsurance(
//                "application/json" , jsonObjectMain);
//
//        call.enqueue(new Callback<AddInsurance.ResponseAddInsurance>() {
//            @Override
//            public void onResponse(Call<AddInsurance.ResponseAddInsurance> call,
//                                   Response<AddInsurance.ResponseAddInsurance> response) {
//                if(response.isSuccessful()){
//                   BaseUtils.showToast(AddNewInsuranceActivity.this, "Success from Add insurance");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddInsurance.ResponseAddInsurance> call, Throwable t) {
//
//            }
//        });

    }



    