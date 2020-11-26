package com.thrucare.healthcare.activity.provider.deviceOrder;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AllergiesSearchActivity;
import com.thrucare.healthcare.activity.provider.orders.AddLabTestActivity;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.adapter.AllergiesSearchAdpater;
import com.thrucare.healthcare.databinding.ActivityAddNewDeviceOrderBinding;
import com.thrucare.healthcare.pojo.ModelCodeAndDisplay;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.pojo.ResponseMedicationStatusAPI;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewDeviceOrderActivity extends AppCompatActivity implements View.OnClickListener,
        AllergiesSearchAdpater.SetOnClickAllergiesSearch, AllergiesReactionAdapter.SetOnClickAttachmentListener {

    private ActivityAddNewDeviceOrderBinding binding;
    private RecyclerView recyclerView;
    ApiInterface mApisService;
    LinearLayoutManager layoutManager;
    private List<ReactionModel> list = new ArrayList<>();
    private AllergiesReactionAdapter mReasonAdapter;
    static AddNewDeviceOrderActivity activity;
    private Calendar myCalendar;
    private List<String> optionsListStatus = new ArrayList<>();
    private List<ModelCodeAndDisplay> optionsListStatusModel = new ArrayList<>();
    private String spinnerStatus;
    private String displayType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewDeviceOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        activity = this;

        // inti  view
        intiView();

        // set calendar view on date view
        setCalerdarView();

        // method for get status for device api calling
        getStatusDeviceApiCalling();
    }

    private void setCalerdarView() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        binding.dateDeviceOrder.setOnClickListener(v -> {
            // TODO Auto-generated method stub

            new DatePickerDialog(AddNewDeviceOrderActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateDeviceOrder.setText(sdf.format(myCalendar.getTime()));
    }

    public static AddNewDeviceOrderActivity getInstance() {
        return activity;
    }

    private void intiView() {
        mApisService = ApiUtils.getApiService();
        recyclerView = binding.recyclerViewDeviceAddReason;
        layoutManager = new LinearLayoutManager(this);

        list.add(new ReactionModel(1, "Reason", ""));
        mReasonAdapter = new AllergiesReactionAdapter(list,
                this, ConstantsUtils.ADD_REASONS_DEVICE_ORDER);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mReasonAdapter);
        mReasonAdapter.notifyDataSetChanged();

        binding.edtTypeDeviceOrder.setOnClickListener(this);
        binding.tvAddMoreDeviceOrder.setOnClickListener(this);
        binding.tvSaveAddDeviceOrder.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.edt_type_device_order:
                startActivity(new Intent(AddNewDeviceOrderActivity.this, AllergiesSearchActivity.class)
                        .putExtra("SEARCH_FOR", ConstantsUtils.ADD_TYPE_DEVICE_ORDER));

                break;

            case R.id.tv_add_more_device_order:
                finish();
                startActivity(new Intent(AddNewDeviceOrderActivity.this, AddNewDeviceOrderActivity.class));
                saveAddNewDeviceApiCalling();
                break;
            case R.id.tv_save_add_device_order:
                saveAddNewDeviceApiCalling();
                AddNewDeviceOrderActivity.this.onBackPressed();
                break;

        }
    }

    private void saveAddNewDeviceApiCalling() {
        JsonObject jsonMainObject  = new JsonObject();
        jsonMainObject.addProperty("category" , binding.edtCategoryName.getText().toString());

        JsonObject jsonObjectType  = new JsonObject();
        jsonObjectType.addProperty("code",  displayType);
        jsonMainObject.add("type" , jsonObjectType);
        jsonMainObject.addProperty("quantity" , binding.edtQuantity.getText().toString());
        JsonArray jsonArray  = new JsonArray();
        for(int i =0 ; i<list.size() ; i++){
            JsonObject jsonObject  = new JsonObject();
            jsonObject.addProperty("code", list.get(i).getReactionValueForAPI());
        }
        jsonMainObject.add("reason", jsonArray);

        JsonObject jsonObjectStatus  = new JsonObject();
        jsonObjectStatus.addProperty("code" , spinnerStatus);
        jsonMainObject.add("status" , jsonObjectStatus);
        jsonMainObject.addProperty("created_date" , binding.dateDeviceOrder.getText().toString());

        JsonObject jsonObjectProvider  = new JsonObject();
        jsonObjectProvider.addProperty("uuid" , "123e4567-e89b-12d3-a456-426614174000");
        jsonMainObject.add("provider" , jsonObjectProvider);

        jsonMainObject.addProperty("notes" , binding.edtAddDeviceNote.getText().toString().trim());


        Call<ResponseBody>   call  = mApisService.saveAddNewDeviceOrder("application/json" , jsonMainObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    BaseUtils.showToast(AddNewDeviceOrderActivity.this  , "Added Device successfully ");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getValueForSubstance(String display) {
        binding.edtTypeDeviceOrder.setText(display);
        displayType  = display;
    }

    @Override
    public void getValueForReaction(String display) {
        try {
            int adpaterPosition = PreferenceManager.getDefaultSharedPreferences(this).getInt("ADAPTER_POSITION", 0);
            list.get(adpaterPosition).setReactionValueForAPI(display);
            mReasonAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int position) {

    }


    private void getStatusDeviceApiCalling() {
        Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call = mApisService.getMedeicationStatus();
        call.enqueue(new Callback<ResponseMedicationStatusAPI.ResponseMedicationStatus>() {
            @Override
            public void onResponse(Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> call,
                                   Response<ResponseMedicationStatusAPI.ResponseMedicationStatus> response) {
                List<ResponseMedicationStatusAPI.Item> reObj = response.body().getItems();

                for (int i = 0; i < reObj.size(); i++) {
                    optionsListStatus.add(reObj.get(i).getDisplay());
                    optionsListStatusModel.add(new ModelCodeAndDisplay(reObj.get(i).getCode(),
                            reObj.get(i).getDisplay()));
                }
                if (optionsListStatus.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewDeviceOrderActivity.this, R.layout.simple_item_spinner,
                            optionsListStatus);
                    binding.spinnerStatusDevice.setAdapter(adapter); // this will set list of values to spinner
                    binding.spinnerStatusDevice.setSelection(optionsListStatus.indexOf(optionsListStatus.get(0)));
                    binding.spinnerStatusDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

}