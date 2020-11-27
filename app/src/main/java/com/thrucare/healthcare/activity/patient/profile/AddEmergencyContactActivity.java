package com.thrucare.healthcare.activity.patient.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityEmergencyContactBinding;
import com.thrucare.healthcare.pojo.EmergencyContactList;
import com.thrucare.healthcare.pojo.modelClasses.PatientEmergencyContact;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmergencyContactActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEmergencyContactBinding binding;
    private ApiInterface PatientServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmergencyContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        PatientServiceApi = PatientUtils.getPatientService();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        // intiView
         intiView();
       // getEmergencyContactDetail();
        setDisableClickable();


    }

    private void intiView() {
        binding.btnSubmitEmergency.setOnClickListener(this);
    }

    private void setDisableClickable() {
        binding.edtPhoneNumnerEmergency.setEnabled(false);
      //  binding.edtFirstNameEmergency.setEnabled(false);
        binding.edtMiddleNameEmergency.setEnabled(false);
        binding.edtLastNameEmergency.setEnabled(false);
        binding.edtEmailAddressEmergency.setEnabled(false);
    }

//    private void getEmergencyContactDetail() {
//        Call<EmergencyContactList.ResponseEmergencyContact> call  = mAPiService.getEmergencyContact();
//        call.enqueue(new Callback<EmergencyContactList.ResponseEmergencyContact>() {
//            @Override
//            public void onResponse(Call<EmergencyContactList.ResponseEmergencyContact> call,
//                                   Response<EmergencyContactList.ResponseEmergencyContact> response) {
//
//                EmergencyContactList.Item itemObj  = response.body().getItems().get(0);
//
//                binding.edtFirstNameEmergency.setText(itemObj.getFirstName());
//                binding.edtMiddleNameEmergency.setText(itemObj.getMiddleName());
//                binding.edtLastNameEmergency.setText(itemObj.getLastName());
//                binding.edtEmailAddressEmergency.setText(itemObj.getEmail());
//                binding.edtPhoneNumnerEmergency.setText(itemObj.getPhone());
//            }
//
//            @Override
//            public void onFailure(Call<EmergencyContactList.ResponseEmergencyContact> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.emergency_contact_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.edit_emergency:
                binding.edtPhoneNumnerEmergency.setEnabled(true);
                binding.edtFirstNameEmergency.setEnabled(true);
                binding.edtMiddleNameEmergency.setEnabled(true);
                binding.edtLastNameEmergency.setEnabled(true);
                binding.edtEmailAddressEmergency.setEnabled(true);
                binding.btnSubmitEmergency.setText("Update");

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit_emergency:

                saveEmergencyContact();

                break;
        }
    }

    private void saveEmergencyContact() {
        JsonObject jsonObjectMain  = new JsonObject();
        jsonObjectMain.addProperty("first_name" , binding.edtFirstNameEmergency.getText().toString());
        jsonObjectMain.addProperty("middle_name" , binding.edtMiddleNameEmergency.getText().toString());
        jsonObjectMain.addProperty("last_name" , binding.edtLastNameEmergency.getText().toString());
        jsonObjectMain.addProperty("email" , binding.edtEmailAddressEmergency.getText().toString());
        jsonObjectMain.addProperty("phone" , binding.edtPhoneNumnerEmergency.getText().toString());

        JsonObject jsonObjectRelationShip = new JsonObject();
        jsonObjectRelationShip.addProperty("code" , "C");
        jsonObjectRelationShip.addProperty("display" , "Emergency Contact");
        jsonObjectMain.add("relationship" , jsonObjectRelationShip);

       Call<PatientEmergencyContact> call = PatientServiceApi.savePatientEmergencyContact("application/json" , jsonObjectMain
               , PreferenceUtils.retriveData(this , "PUUID_PATIENT"), ConstantsUtils.API_KEY);

       call.enqueue(new Callback<PatientEmergencyContact>() {
           @Override
           public void onResponse(Call<PatientEmergencyContact> call, Response<PatientEmergencyContact> response) {
                    if(response.code() ==202)
                    {
                        BaseUtils.showToast(AddEmergencyContactActivity.this , "Emergency Contact Added");
                        onBackPressed();
                    }else
                    {
                        BaseUtils.showToast(AddEmergencyContactActivity.this , "Error adding Contact"+response.code());
                    }

           }

           @Override
           public void onFailure(Call<PatientEmergencyContact> call, Throwable t) {
               BaseUtils.showToast(AddEmergencyContactActivity.this , "Emergency Contact Added");
           }
       });

    }
}