package com.thrucare.healthcare.activity.commanActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rilixtech.CountryCodePicker;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.modelClasses.PatientRegister;
import com.thrucare.healthcare.pojo.modelClasses.ProviderRegister;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.databinding.ActivityRegistrationBinding;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity" ;
    ActivityRegistrationBinding binding;
    private ApiInterface mApiService;
    List<String> listGender   = new ArrayList<>();
    private Calendar myCalendar;
    private ProgressDialog progressDialog;
    private CountryCodePicker ccp;
    private EditText edtPhoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        listGender.add("Male");
        listGender.add("Female");
        //progress dialog
        progressDialogue();

        // get intent and set value according the user type
        getIntentValue();

        // set value on gender view
        setSpinnerDataOnGender();

        // set calendar view on DOD
        setCalendarOnDOBView();

        // mobile number and flag

        getFlagOfCountryListener();

        //country code library for getting +91
        countryCode();
    }

    private String countryCode() {
        ccp = binding.ccp;
        edtPhoneNumber = binding.editEnterPhone;
        ccp.registerPhoneNumberTextView(edtPhoneNumber);
       return ccp.getFullNumberWithPlus();
    }

    private void progressDialogue() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait..");
        progressDialog.setCancelable(false);
    }

    private void getFlagOfCountryListener() {
       /* phoneInputLayout = (PhoneInputLayout) findViewById(R.id.phone_input_layout);*/

      // final PhoneEditText phoneEditText = (PhoneEditText) findViewById(R.id.edit_text);
        //final Button button = (Button) findViewById(R.id.submit_button);

/*// you can set the hint as follows
        phoneInputLayout.setHint(R.string.phone_hint);
        phoneEditText.setHint(R.string.phone_hint);*/

// you can set the default country as follows
       // phoneInputLayout.setDefaultCountry("US");
       // phoneEditText.setDefaultCountry("US");



        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                // checks if the field is valid
                if (phoneInputLayout.isValid()) {
                    phoneInputLayout.setError(null);
                } else {
                    // set error message
                    phoneInputLayout.setError(getString(R.string.invalid_phone_number));
                    valid = false;
                }

                if (phoneEditText.isValid()) {
                    phoneEditText.setError(null);
                } else {
                    phoneEditText.setError(getString(R.string.invalid_phone_number));
                    valid = false;
                }

                if (valid) {
                    Toast.makeText(MainActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
                }

                // Return the phone number as follows
                String phoneNumber = phoneInputLayout.getPhoneNumber();
            }
        });*/

    }

    private void setCalendarOnDOBView() {
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
        binding.editDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistrationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateLabel() {
        String myFormat = ConstantsUtils.DATE_FORMAT; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.editDob.setText(sdf.format(myCalendar.getTime()));
    }

    private void setSpinnerDataOnGender() {
        if (listGender.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistrationActivity.this, R.layout.simple_item_spinner, listGender);
            binding.editGender.setAdapter(adapter); // this will set list of values to spinner
            binding.editGender.setSelection(listGender.indexOf(listGender.get(0)));
        }
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        String valueForReg  = intent.getStringExtra(ConstantsUtils.ForRegister);

        if(valueForReg.equalsIgnoreCase(ConstantsUtils.provider)){
            binding.tvForWhom.setText(ConstantsUtils.For_Provider);
        }else {
            binding.tvForWhom.setText(ConstantsUtils.For_Patient);
        }
        mApiService = ApiUtils.getApiService();
        binding.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = binding.editFirstName.getText().toString().trim();
                String middle_name = binding.editMiddleName.getText().toString().trim();
                String last_name = binding.editLastName.getText().toString().trim();
                String email = binding.editEnterEmail.getText().toString().trim();
                String phone = ccp.getFullNumberWithPlus();
                String photo = "url";
                String bloodGroup = "Blood group A";
                String dobPatient = binding.editDob.getText().toString().concat("T00:00:00.000Z");
                String dobProvider = binding.editDob.getText().toString();
                String gender = binding.editGender.getSelectedItem().toString();

                if(TextUtils.isEmpty(first_name)){
                    Toast.makeText(RegistrationActivity.this,"Please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(middle_name)){
                    Toast.makeText(RegistrationActivity.this,"Please enter middle name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(last_name)){
                    Toast.makeText(RegistrationActivity.this,"Please enter last name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrationActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(RegistrationActivity.this,"Please enter phone",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dobPatient)){
                    Toast.makeText(RegistrationActivity.this,"Please Select date of birth",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dobProvider)){
                    Toast.makeText(RegistrationActivity.this,"Please Select date of birth",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(gender)){
                    Toast.makeText(RegistrationActivity.this,"Please select gender",Toast.LENGTH_LONG).show();
                    return;

                }
                progressDialog.show();

               if(valueForReg.equalsIgnoreCase(ConstantsUtils.provider))
                createProviderApiCalling(gender , first_name , middle_name , last_name , email , phone , dobProvider);
                else
               {
                  createPatientApiCalling(gender , first_name , middle_name , last_name , email , phone , dobPatient , photo , bloodGroup);
               }

            }
        });
    }

    private void createPatientApiCalling(String gender , String first_name , String middle_name , String last_name , String email , String phone , String dob , String photo , String bloodGroup) {


        JsonObject jsonObject =  new JsonObject();

        JsonArray jsonArrayIdentifier = new JsonArray();
        JsonObject jsonObjectIdentifier  = new JsonObject();
        jsonObjectIdentifier.addProperty("type" , "SSN");
        jsonObjectIdentifier.addProperty("value" , "1122334499");
        jsonArrayIdentifier.add(jsonObjectIdentifier);

        JsonObject genderObject =  new JsonObject();
        genderObject.addProperty("code" , gender );
        genderObject.addProperty("display" , gender);

        JsonObject addressObject =  new JsonObject();
        addressObject.addProperty("line1" , "1234");
        addressObject.addProperty("line2" , "OneSt");
        addressObject.addProperty("city" , "Gwalior");
        addressObject.addProperty("state" , "MadhyaPradesh");
        addressObject.addProperty("country" , "india");

        JsonObject bloodObject =  new JsonObject();
        bloodObject.addProperty("code" , bloodGroup );
        bloodObject.addProperty("display" , bloodGroup);

        jsonObject.addProperty("first_name",first_name);
        jsonObject.addProperty("middle_name", middle_name);
        jsonObject.addProperty("last_name", last_name);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("phone", countryCode());
        jsonObject.addProperty("birth_date", dob);
        jsonObject.addProperty("photo", photo);
        jsonObject.add("identifier" , jsonArrayIdentifier);
        jsonObject.add("gender", genderObject);
        jsonObject.add("address", addressObject);
        jsonObject.add("blood_group", bloodObject);


        Call<PatientRegister> call = mApiService.createUserPatient("application/json", jsonObject , ConstantsUtils.API_KEY);
        call.enqueue(new Callback<PatientRegister>() {
            @Override
            public void onResponse(Call<PatientRegister> call, Response<PatientRegister> response) {

                if(response.code() == 202)
                {
                    progressDialog.dismiss();
                    BaseUtils.showToast(RegistrationActivity.this , "Patient created successfully");
                    PreferenceUtils.insertData(RegistrationActivity.this , "PUUID_PATIENT"  , response.body().getUuid());
                    Log.d("UUID_PATIENT", "onResponse: "+response.body().getUuid());
                }
                else
                    BaseUtils.showToast(RegistrationActivity.this , "Provider registration failed"+ response.message());

            }

            @Override
            public void onFailure(Call<PatientRegister> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this , "onFail: "  + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createProviderApiCalling(String gender , String first_name , String middle_name , String last_name , String email , String phone , String dob) {

        JsonObject jsonObject =  new JsonObject();
        JsonObject genderObject =  new JsonObject();


             genderObject.addProperty("code" , gender );
             genderObject.addProperty("display" , gender);

            jsonObject.addProperty("first_name",first_name);
            jsonObject.addProperty("middle_name", middle_name);
            jsonObject.addProperty("last_name", last_name);
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("phone", phone);
            jsonObject.addProperty("birth_date", dob);
            jsonObject.add("gender", genderObject);



        Call<ProviderRegister> call = mApiService.createUser(getResources().getString(R.string.application_json), jsonObject , ConstantsUtils.API_KEY);
        call.enqueue(new Callback<ProviderRegister>() {
            @Override
            public void onResponse(Call<ProviderRegister> call, Response<ProviderRegister> response) {

                if(response.code() == 202)
                {
                    progressDialog.dismiss();
                    BaseUtils.showToast(RegistrationActivity.this , "Provider created successfully");
                    PreferenceUtils.insertData(RegistrationActivity.this , "PUUID"  , response.body().getUuid());
                    Log.d("UUID", "onResponse: "+response.body().getUuid());
                }
                else
                    BaseUtils.showToast(RegistrationActivity.this , "Provider registration failed"+ response.code());

            }

            @Override
            public void onFailure(Call<ProviderRegister> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this , "onFail: "  + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void signUpApiCalling() {
//        JsonObject jsonObject  = new JsonObject();
//
//        try {
//            jsonObject.addProperty("first_name", String.valueOf(binding.editFirstName.getText()));
//            jsonObject.addProperty("middle_name", String.valueOf(binding.editMiddleName.getText()));
//            jsonObject.addProperty("last_name", String.valueOf(binding.editLastName.getText()));
//            jsonObject.addProperty("email", String.valueOf(binding.editEnterEmail.getText()));
//           // jsonObject.addProperty("phone", String.valueOf(binding.editPhone.getText));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        Call<PatientRegistrationResponse> call = mApiService.signUp(getResources().getString(R.string.application_json), jsonObject);
//
//        call.enqueue(new Callback<PatientRegistrationResponse>() {
//            @Override
//            public void onResponse(Call<PatientRegistrationResponse> call, final Response<PatientRegistrationResponse> response) {
//
//                if (response.code() == 201) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), response.body().getUuid(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//
//
//
//                }
//            }
//
//
//            @Override
//            public void onFailure (Call < PatientRegistrationResponse > call, Throwable t){
//                Log.d("BankSelection", "onResponse: " + t);
//            }
//        });
//
//
//    }
}