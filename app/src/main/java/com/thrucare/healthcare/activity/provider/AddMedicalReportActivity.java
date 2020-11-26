package com.thrucare.healthcare.activity.provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAddDiagnosisBinding;
import com.thrucare.healthcare.databinding.ActivityAddMedicalReportBinding;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ReportCategory;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicalReportActivity extends AppCompatActivity implements View.OnClickListener,
        AllergiesSearchAdpater.SetOnClickTypeMedicalReport , AllergiesReactionAdapter.SetOnClickAttachmentListener {

    private static final int PICK_IMAGE = 1;
    private ActivityAddMedicalReportBinding binding;
    static AddMedicalReportActivity activity;
    private ApiInterface mAPiService;
    private List<String> optionsListCategory = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListCategoryModel = new ArrayList<>();

    private List<String> optionsListStatus = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private RecyclerView recyclerViewAttachment;
    private LinearLayoutManager layoutManager;
    private List<ReactionModel> list = new ArrayList<>();
    private AllergiesReactionAdapter mAdapter;
    private Uri imageUri;
    private int positionAttachment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMedicalReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mAPiService = ApiUtils.getApiService();
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        intiView();
        getReportCategoryDataApiCalling();
        getMedicationStatusDataApiCalling();
    }

    public static AddMedicalReportActivity getInstance() {
        return activity;
    }

    private void intiView() {
        binding.edtTypeMedicalReport.setOnClickListener(this);
        binding.edtConclusionMedicalReport.setOnClickListener(this);
        binding.tvAddMoreMedication.setOnClickListener(this);
        binding.tvSaveAddMedicationReport.setOnClickListener(this);
        recyclerViewAttachment  = binding.recylerViewAttachment;
        layoutManager  = new LinearLayoutManager(this);
        list.add(new ReactionModel(1, "Attachment" , ""));
        mAdapter = new AllergiesReactionAdapter(list,
                this , ConstantsUtils.ADD_ATTACHMENT);
        recyclerViewAttachment.setLayoutManager(layoutManager);
        recyclerViewAttachment.setAdapter(mAdapter);
    }

    private void getReportCategoryDataApiCalling() {
        Call<ReportCategory.ResponseReportCategory> call = mAPiService.getReportCategory();

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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMedicalReportActivity.this, R.layout.simple_item_spinner,
                            optionsListCategory);
                    binding.spinnerReportCategory.setAdapter(adapter); // this will set list of values to spinner

                    binding.spinnerReportCategory.setSelection(optionsListCategory.indexOf(optionsListCategory.get(0)));
                }

            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getMedicationStatusDataApiCalling() {

        Call<ReportCategory.ResponseReportCategory> call = mAPiService.getMedicationReportStatus();

        call.enqueue(new Callback<ReportCategory.ResponseReportCategory>() {
            @Override
            public void onResponse(Call<ReportCategory.ResponseReportCategory> call,
                                   Response<ReportCategory.ResponseReportCategory> response) {
                ReportCategory.ResponseReportCategory reObj = response.body();

                for (int i = 0; i < reObj.getItems().size(); i++) {
                    optionsListStatus.add(reObj.getItems().get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.getItems().get(i).getCode(),
                            reObj.getItems().get(i).getDisplay()));
                }
                if (optionsListStatus.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMedicalReportActivity.this, R.layout.simple_item_spinner,
                            optionsListStatus);
                    binding.edtStatusMedicalReport.setAdapter(adapter); // this will set list of values to spinner

                    binding.edtStatusMedicalReport.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                }

            }

            @Override
            public void onFailure(Call<ReportCategory.ResponseReportCategory> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_type_medical_report:
                startActivity(new Intent(this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "ADD_MEDICAL_PROCEDURE"));
                break;


            case R.id.edt_conclusion_medical_report:
                startActivity(new Intent(this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "ADD_MEDICAL_CONCLUSION"));
                break;

            case R.id.tv_add_more_medication:
                finish();
                startActivity(new Intent(AddMedicalReportActivity.this, AddMedicalReportActivity.class));
                BaseUtils.showToast(this, "Success");
                break;


            case R.id.tv_save_add_medication_report:
                BaseUtils.showToast(this, "Success");
                AddMedicalReportActivity.this.onBackPressed();
                break;

        }
    }

    @Override
    public void onClick(String display) {
        binding.edtTypeMedicalReport.setText(display);
    }

    @Override
    public void onClickConclusion(String display) {
        binding.edtConclusionMedicalReport.setText(display);
    }

    @Override
    public void onClick(int position) {
        positionAttachment  = position;
        openGallery(position);
    }

    private void openGallery(int positionAttachment) {
         Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, positionAttachment);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == positionAttachment){
            imageUri = data.getData();
            //imageView.setImageURI(imageUri);

            Log.d("imageUri" , String.valueOf(imageUri));
            BaseUtils.showToast(AddMedicalReportActivity.this , "File Uploaded Successfully");
            list.get(positionAttachment).setReactionValueForAPI("File"+positionAttachment);
            mAdapter.notifyDataSetChanged();
        }
    }
    
}