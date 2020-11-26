package com.thrucare.healthcare.activity.provider.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AllergiesSearchActivity;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAddLabTestBinding;
import com.thrucare.healthcare.pojo.LabTestOrderResponse;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ResponseMedicationStatusAPI;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
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

public class AddLabTestActivity extends AppCompatActivity  implements AllergiesSearchAdpater.SetOnClickAllergiesSearch
        , View.OnClickListener , AllergiesSearchAdpater.SetOnClickPerformer {

    private ActivityAddLabTestBinding binding;
    private String orderType;
    private ApiInterface mApisService;
    private RecyclerView recyclerView;
    private List<ReactionModel> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private AllergiesReactionAdapter mReasonAdapter;
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private List<String> optionsListStatus  = new ArrayList<>();
    private Calendar myCalendar;
    static AddLabTestActivity activity;
    private String perfomerTypeCode,procedureCode,spinnerStatus,createdDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLabTestBinding.inflate(getLayoutInflater());
        activity = this;
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        try{
            orderType   = getIntent().getStringExtra(ConstantsUtils.ORDER_TYPE);
        }catch (Exception e){

        }



        setListeners();
        mApisService = ApiUtils.getApiService();
        getStatusMedicationApiCalling();
        recyclerView  =  view.findViewById(R.id.recyclerViewMedicationReaction_order_other);
        layoutManager = new LinearLayoutManager(this);

        list.add(new ReactionModel(1, "Reason" , ""));
        mReasonAdapter = new AllergiesReactionAdapter(list,
                this , ConstantsUtils.ADD_LAB_OTHER_ORDERS);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mReasonAdapter);
        mReasonAdapter.notifyDataSetChanged();

        setListeners();

        if(orderType.equalsIgnoreCase(ConstantsUtils.LAB_ORDER)){
            binding.tvAddOtherOrder.setText("Add Lab test");
        }else  if(orderType.equalsIgnoreCase(ConstantsUtils.RADIOLOGY_ORDER)){
            binding.tvAddOtherOrder.setText("Add Radiology test");
        }else if(orderType.equalsIgnoreCase(ConstantsUtils.REFFERAL_ORDER)){
            binding.tvAddOtherOrder.setText("Add Referral test");
            if(!PreferenceUtils.retriveData(this , "PerformerName").isEmpty()){
                binding.edtAddLabTestPerformerName.setText(PreferenceUtils.retriveData( this  , "PerformerName"));
                if(PreferenceUtils.retriveData( this  , "Procedure").length()>25){
                    binding.edtProcedureOrder.setText(PreferenceUtils.retriveData( this  , "Procedure").substring(0 , 22)+"...");
                }else {
                    binding.edtProcedureOrder.setText(PreferenceUtils.retriveData( this  , "Procedure"));
                }
                binding.edtPerformerType.setText(PreferenceUtils.retriveData( this  , "PerformerType"));
                binding.dateMedicationProviderOrder.setText(PreferenceUtils.retriveData( this  , "Date"));
                binding.spinnerStatusMedicationOrderOther.setSelection(optionsListStatusModel.indexOf(
                        PreferenceUtils.retriveData( this  , "Status")));
                binding.tvSaveAddMedicationReport.setVisibility(View.GONE);
                binding.tvAddMoreMedication.setText("Update");


            }

        }
    }

    public  static AddLabTestActivity getInstance(){

        return activity;
    }
    private void setListeners() {
        binding.edtPerformerType.setOnClickListener(this);
        binding.edtProcedureOrder.setOnClickListener(this);
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
        binding.dateMedicationProviderOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(AddLabTestActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.tvSaveAddMedicationReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderType.equalsIgnoreCase(ConstantsUtils.LAB_ORDER)){
                    //labtestapi
                    saveLabTestApiCalling();
                }else  if(orderType.equalsIgnoreCase(ConstantsUtils.RADIOLOGY_ORDER)){
                    //radiology api
                    saveRadioTestApiCalling();

                }
                else  if(orderType.equalsIgnoreCase(ConstantsUtils.REFFERAL_ORDER)){
                    //radiology api
                    saveReferralTestApiCalling();

                }
            }
        });

    }

    private void saveReferralTestApiCalling() {
        JsonObject object = new JsonObject();

        JsonObject performanceTypeObject = new JsonObject();
        performanceTypeObject.addProperty("code",perfomerTypeCode);

        JsonObject performerObject = new JsonObject();
        performerObject.addProperty("name",binding.edtAddLabTestPerformerName.getText().toString());
        performerObject.add("type",performanceTypeObject);

        JsonObject procedureObjject = new JsonObject();
        procedureObjject.addProperty("code",procedureCode);

        JsonObject reasonObject = new JsonObject();
        for (int i = 0; i<list.size();i++){
            reasonObject.addProperty("code",list.get(i).getReactionValueForAPI());
            Log.d("li",list.get(i).getReactionValueForAPI());
        }
        JsonArray array = new JsonArray();
        array.add(reasonObject);

        JsonObject statusObject = new JsonObject();
        statusObject.addProperty("code",spinnerStatus);

        JsonObject providerObject = new JsonObject();
        providerObject.addProperty("uuid","123e4567-e89b-12d3-a456-426614174000");


        object.add(getResources().getString(R.string.performer),performerObject);
        object.add(getResources().getString(R.string.procedure),procedureObjject);
        object.add(getResources().getString(R.string.reason),array);
        object.add(getResources().getString(R.string.status),statusObject);
        object.addProperty(getResources().getString(R.string.created_date),createdDate);
        object.add(getResources().getString(R.string.provider),providerObject);
        object.addProperty(getResources().getString(R.string.notes),binding.edtAddLabTestNote.getText().toString());

        Log.d("radio-text",object.toString());

        Call<LabTestOrderResponse> call = mApisService.addReferralOrders(getResources().getString(R.string.application_json),object);
        call.enqueue(new Callback<LabTestOrderResponse>() {
            @Override
            public void onResponse(Call<LabTestOrderResponse> call, Response<LabTestOrderResponse> response) {
                Toast.makeText(AddLabTestActivity.this, "Referral Test Success", Toast.LENGTH_SHORT).show();
                AddLabTestActivity.this.onBackPressed();
            }

            @Override
            public void onFailure(Call<LabTestOrderResponse> call, Throwable t) {

            }
        });
    }

    private void saveRadioTestApiCalling() {
        JsonObject object = new JsonObject();

        JsonObject performanceTypeObject = new JsonObject();
        performanceTypeObject.addProperty("code",perfomerTypeCode);

        JsonObject performerObject = new JsonObject();
        performerObject.addProperty("name",binding.edtAddLabTestPerformerName.getText().toString());
        performerObject.add("type",performanceTypeObject);

        JsonObject procedureObjject = new JsonObject();
        procedureObjject.addProperty("code",procedureCode);

        JsonObject reasonObject = new JsonObject();
        for (int i = 0; i<list.size();i++){
            reasonObject.addProperty("code",list.get(i).getReactionValueForAPI());
            Log.d("li",list.get(i).getReactionValueForAPI());
        }
        JsonArray array = new JsonArray();
        array.add(reasonObject);

        JsonObject statusObject = new JsonObject();
        statusObject.addProperty("code",spinnerStatus);

        JsonObject providerObject = new JsonObject();
        providerObject.addProperty("uuid","123e4567-e89b-12d3-a456-426614174000");


        object.add(getResources().getString(R.string.performer),performerObject);
        object.add(getResources().getString(R.string.procedure),procedureObjject);
        object.add(getResources().getString(R.string.reason),array);
        object.add(getResources().getString(R.string.status),statusObject);
        object.addProperty(getResources().getString(R.string.created_date),createdDate);
        object.add(getResources().getString(R.string.provider),providerObject);
        object.addProperty(getResources().getString(R.string.notes),binding.edtAddLabTestNote.getText().toString());

        Log.d("radio-text",object.toString());

        Call<LabTestOrderResponse> call = mApisService.addLabTestOrders(getResources().getString(R.string.application_json),object);
        call.enqueue(new Callback<LabTestOrderResponse>() {
            @Override
            public void onResponse(Call<LabTestOrderResponse> call, Response<LabTestOrderResponse> response) {
                Toast.makeText(AddLabTestActivity.this, "radiology Order Success", Toast.LENGTH_SHORT).show();
                AddLabTestActivity.this.onBackPressed();
            }

            @Override
            public void onFailure(Call<LabTestOrderResponse> call, Throwable t) {

            }
        });
    }

    private void saveLabTestApiCalling() {
        JsonObject object = new JsonObject();

        JsonObject performanceTypeObject = new JsonObject();
        performanceTypeObject.addProperty("code",perfomerTypeCode);

        JsonObject performerObject = new JsonObject();
        performerObject.addProperty("name",binding.edtAddLabTestPerformerName.getText().toString());
        performerObject.add("type",performanceTypeObject);

        JsonObject procedureObjject = new JsonObject();
        procedureObjject.addProperty("code",procedureCode);

        JsonObject reasonObject = new JsonObject();
        for (int i = 0; i<list.size();i++){
            reasonObject.addProperty("code",list.get(i).getReactionValueForAPI());
            Log.d("li",list.get(i).getReactionValueForAPI());
        }
        JsonArray array = new JsonArray();
        array.add(reasonObject);

        JsonObject statusObject = new JsonObject();
        statusObject.addProperty("code",spinnerStatus);

        JsonObject providerObject = new JsonObject();
        providerObject.addProperty("uuid","123e4567-e89b-12d3-a456-426614174000");


        object.add(getResources().getString(R.string.performer),performerObject);
        object.add(getResources().getString(R.string.procedure),procedureObjject);
        object.add(getResources().getString(R.string.reason),array);
        object.add(getResources().getString(R.string.status),statusObject);
        object.addProperty(getResources().getString(R.string.created_date),createdDate);
        object.add(getResources().getString(R.string.provider),providerObject);
        object.addProperty(getResources().getString(R.string.notes),binding.edtAddLabTestNote.getText().toString());

        Log.d("text",object.toString());

        Call<LabTestOrderResponse> call = mApisService.addLabTestOrders(getResources().getString(R.string.application_json),object);
        call.enqueue(new Callback<LabTestOrderResponse>() {
            @Override
            public void onResponse(Call<LabTestOrderResponse> call, Response<LabTestOrderResponse> response) {
                Toast.makeText(AddLabTestActivity.this, "Lab Order Success", Toast.LENGTH_SHORT).show();
                AddLabTestActivity.this.onBackPressed();
            }

            @Override
            public void onFailure(Call<LabTestOrderResponse> call, Throwable t) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateMedicationProviderOrder.setText(sdf.format(myCalendar.getTime()));
        createdDate = sdf.format(myCalendar.getTime());
    }
    private void getStatusMedicationApiCalling() {
        Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call  = mApisService.getMedeicationStatus();
        call.enqueue(new Callback<ResponseMedicationStatusAPI.ResponseMedicationStatus>() {
            @Override
            public void onResponse(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call,
                                   Response<ResponseMedicationStatusAPI.ResponseMedicationStatus> response) {
                List<ResponseMedicationStatusAPI.Item> reObj  = response.body().getItems();

                for(int i =0 ; i<reObj.size() ; i++){
                    optionsListStatus.add(reObj.get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.get(i).getCode(),
                            reObj.get(i).getDisplay()));
                }
                if(optionsListStatus.size()>0){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddLabTestActivity.this, R.layout.simple_item_spinner,
                            optionsListStatus);
                    binding.spinnerStatusMedicationOrderOther.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusMedicationOrderOther.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                    binding.spinnerStatusMedicationOrderOther.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerStatus = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getValueForSubstance(String display) {

    }

    @Override
    public void getValueForReaction(String display) {
        try{
            int adpaterPosition  = PreferenceManager.getDefaultSharedPreferences(this).getInt("ADAPTER_POSITION" , 0);
            list.get(adpaterPosition).setReactionValueForAPI(display);
            mReasonAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edt_performer_type:
                startActivity(new Intent(this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "ADD_PERFORMER_TYPE"));
                break;


            case R.id.edt_procedure_order:
                startActivity(new Intent(this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", "ADD_PROCEDURE"));
                break;
        }
    }

    @Override
    public void onClick(String displayName, String displayCode) {
        binding.edtPerformerType.setText(displayName);
        perfomerTypeCode = displayCode;
    }

    @Override
    public void onClickProcedure(String displayName, String displayCode) {
        binding.edtProcedureOrder.setText(displayName);
        procedureCode = displayCode;
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