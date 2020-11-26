package com.thrucare.healthcare.activity.patient.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.DigitalSignatureActivity;
import com.thrucare.healthcare.activity.provider.insurance.InsuranceListActivity;
import com.thrucare.healthcare.activity.provider.qualification.QualificationListActivity;
import com.thrucare.healthcare.databinding.ActivityProfileInfoBinding;
import com.thrucare.healthcare.pojo.modelClasses.UpdateResponseProfile;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileInfoActivity extends AppCompatActivity implements OnClickListener {

    private ActivityProfileInfoBinding binding;
    static ProfileInfoActivity activity;
    private ApiInterface mAPiService;
    private Calendar dateSelected = Calendar.getInstance();
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        //mAPiService = ApiUtils.getApiService();
        mAPiService = ProviderUtils.getService();
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        userType = getIntent().getStringExtra(ConstantsUtils.USER_TYPE);

        if (userType.equals(ConstantsUtils.patient)){
            Log.d("type",userType);
            binding.tvSignature.setVisibility(View.GONE);
            binding.tvProfileSignature.setVisibility(View.GONE);
            binding.viewBelowSignature.setVisibility(View.GONE);
            binding.tvProfileInsuarance.setOnClickListener(this);
            binding.tvProfileEmergencyContact.setOnClickListener(this);
        }else{
            Log.d("type",userType);
            binding.tvSignature.setVisibility(View.VISIBLE);
            binding.tvProfileSignature.setVisibility(View.VISIBLE);
            binding.viewBelowSignature.setVisibility(View.VISIBLE);
            binding.tvProfileQualification.setVisibility(View.VISIBLE);
            binding.tvQualification.setVisibility(View.VISIBLE);
            binding.viewBelowQualification.setVisibility(View.VISIBLE);
            binding.tvProfileEmergencyContact.setVisibility(View.GONE);
            binding.tvEmergencyContcat.setVisibility(View.GONE);
            binding.viewBelowEmergenyContact.setVisibility(View.GONE);
            binding.tvProfileSignature.setOnClickListener(this);
            binding.tvProfileQualification.setOnClickListener(this);
            binding.tvProfileInsuarance.setOnClickListener(this);
        }

        binding.tvPrifileContact.setEnabled(false);
        binding.tvProfileName.setEnabled(false);
        binding.tvProfileEmail.setEnabled(false);
        binding.tvProfileDate.setEnabled(false);
        binding.tvProfileGender.setEnabled(false);
        binding.tvProfileIdentifier.setEnabled(false);
        //binding.tvProfileInsuarance.setEnabled(false);
        binding.tvProfileAddress.setEnabled(false);
        binding.tvProfileBloodGroup.setEnabled(false);
        binding.tvProfileLanguage.setEnabled(false);
        //binding.tvProfileQualification.setEnabled(false);
        getProfileData();
    }

    private void getProfileData() {
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String dob = getIntent().getStringExtra("dob");
        String gender = getIntent().getStringExtra("gender");
        String identifier = getIntent().getStringExtra("identifier");
        String address = getIntent().getStringExtra("address");
        String bloodGroup = getIntent().getStringExtra("blood_group");
        String insurance = getIntent().getStringExtra("insurance");


        binding.tvProfileName.setText(name);
        binding.tvPrifileContact.setText(phone);
        binding.tvProfileEmail.setText(email);
        binding.tvProfileDate.setText(dob);
        binding.tvProfileGender.setText(gender);
        binding.tvProfileIdentifier.setText(identifier);
        binding.tvProfileAddress.setText(address);
        binding.tvProfileBloodGroup.setText(bloodGroup);



        binding.btnEditProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName, newPhone,newEmail,newDOB,newGender,newIdentifier,newLanguage,
                newAddress,newInsuarance,newEmerganceContact,newBloodGroup;
                String profilePhoto , signature;
                newName = binding.tvProfileName.getText().toString();
                newPhone = binding.tvPrifileContact.getText().toString();
                newEmail = binding.tvProfileEmail.getText().toString();
                newDOB = binding.tvProfileDate.getText().toString();
                newGender = binding.tvProfileGender.getText().toString();
                profilePhoto = "http://images.com/photo123.jpg";
                signature = "http://images.com/signature.jpg";

//                newIdentifier = binding.tvProfileIdentifier.getText().toString();
//                newLanguage = binding.tvProfileLanguage.getText().toString();
//                newAddress = binding.tvProfileAddress.getText().toString();
//                newInsuarance = binding.tvProfileInsuarance.getText().toString();
//                newEmerganceContact = binding.tvProfileEmergencyContact.getText().toString();
//                newBloodGroup  = binding.tvProfileBloodGroup.getText().toString();

                updateProfileData(newName, newPhone,newEmail,newDOB,newGender,profilePhoto , signature);

            }
        });

    }

    private void updateProfileData(String newName, String newPhone, String newEmail, String newDOB, String newGender, String profilePic , String signature) {

        String firstName = newName.split(" ")[0];
        String middleName = newName.split(" ")[1];
        String lastName = newName.split(" ")[2];
        JsonObject jsonObjectMain = new JsonObject();

        JsonObject jsonObjectGender = new JsonObject();
        jsonObjectGender.addProperty("code" , newGender);

        jsonObjectMain.addProperty("first_name" , firstName);
        jsonObjectMain.addProperty("middle_name" , middleName);
        jsonObjectMain.addProperty("last_name" , lastName);
        jsonObjectMain.addProperty("email" , newEmail);
        jsonObjectMain.addProperty("phone" , newPhone);
        jsonObjectMain.addProperty("birth_date" , newDOB);
        jsonObjectMain.add("gender" , jsonObjectGender);
        jsonObjectMain.addProperty("photo" , profilePic);
        jsonObjectMain.addProperty("signature" , signature);

        Call<UpdateResponseProfile> call = mAPiService.updateProfileData("application/json" , jsonObjectMain , PreferenceUtils.retriveData(this , "PUUID"), ConstantsUtils.API_KEY);
        call.enqueue(new Callback<UpdateResponseProfile>() {
            @Override
            public void onResponse(Call<UpdateResponseProfile> call, Response<UpdateResponseProfile> response) {
                BaseUtils.showToast(ProfileInfoActivity.this,"Profile Updated");
                Intent mIntent = getIntent();
                finish();
                startActivity(mIntent);
            }

            @Override
            public void onFailure(Call<UpdateResponseProfile> call, Throwable t) {
                BaseUtils.showToast(ProfileInfoActivity.this,"Profile Updated error");
            }
        });


    }

//    private void updateData(@NotNull String newName, String newPhone, String newEmail, String newDOB, String newGender, String newIdentifier, String newLanguage, String newAddress, String newInsuarance, String newEmerganceContact, String newBloodGroup) {
//
//        String firstName = newName.split(" ")[0];
//        String middleName = newName.split(" ")[1];
//        String lastName = newName.split(" ")[2];
//
//        JsonObject object = new JsonObject();
//
//        JsonObject identifierObject = new JsonObject();
//        identifierObject.addProperty("code","NPI");
//        identifierObject.addProperty("value",newIdentifier);
//
//        JsonArray array = new JsonArray();
//        array.add(identifierObject);
//
//        JsonObject genderObject = new JsonObject();
//        genderObject.addProperty("code",newGender);
//
//        object.add(getResources().getString(R.string.update_identifier),array);
//
//        object.addProperty(getResources().getString(R.string.update_first_name),firstName);
//        object.addProperty(getResources().getString(R.string.update_middle_name),middleName);
//        object.addProperty(getResources().getString(R.string.update_last_name),lastName);
//        object.addProperty(getResources().getString(R.string.update_email),newEmail);
//        object.addProperty(getResources().getString(R.string.update_phone),newPhone);
//        object.add(getResources().getString(R.string.update_gender),genderObject);
//        object.addProperty(getResources().getString(R.string.update_dob),newDOB);
//        Log.d("testObj",object.toString());
//
//
 //     Call<UpdateProfileResponse> call = mAPiService.updateProfile(getResources().getString(R.string.application_json),object);
//        call.enqueue(new Callback<UpdateProfileResponse>() {
//            @Override
//            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
//                BaseUtils.showToast(ProfileInfoActivity.this,"Profile Updated");
//            }
//
//            @Override
//            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.edit_profile:
                binding.btnEditProfile.setVisibility(View.VISIBLE);
                binding.tvPrifileContact.setEnabled(true);
                binding.tvProfileName.setEnabled(true);
                binding.tvProfileEmail.setEnabled(true);
                binding.tvProfileDate.setEnabled(true);
                binding.tvProfileGender.setEnabled(true);
                binding.tvProfileIdentifier.setEnabled(true);
                binding.tvProfileInsuarance.setEnabled(true);
                binding.tvProfileAddress.setEnabled(true);
                binding.tvProfileEmergencyContact.setEnabled(true);
                binding.tvProfileBloodGroup.setEnabled(true);
                binding.tvProfileLanguage.setEnabled(true);
                binding.tvProfileQualification.setEnabled(true);
                binding.tvProfileDate.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar newCalendar = dateSelected;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
                        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateSelected.set(year, monthOfYear+1, dayOfMonth);
                                binding.tvProfileDate.setText(dateFormatter.format(dateSelected.getTime()));
                            }

                        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_profile_signature:
                startActivity(new Intent(this , DigitalSignatureActivity.class));
                break;
            case R.id.tv_profile_qualification:
                startActivity(new Intent(this , QualificationListActivity.class));
                break;


            case R.id.tv_profile_insuarance:
                startActivity(new Intent(this , InsuranceListActivity.class).
                          putExtra(ConstantsUtils.USER_TYPE , userType));
                break;

            case R.id.tv_profile_emergency_contact:
                startActivity(new Intent(this , EmergencyContactActivity.class));
                break;
        }
    }
}



