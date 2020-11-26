package com.thrucare.healthcare.activity.commanActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.databinding.ActivityMainBinding;
import com.thrucare.healthcare.pojo.LoginResponse;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class LoginActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ApiInterface mApiService;
    private String valueForReg;
    private static final int REQUEST_CODE = 100;
    private Executor executer;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        executer = ContextCompat.getMainExecutor(this);
        checkBioMetricValidationOnDevice();

        getIntentValue();

        setListeners();

        if(!PreferenceUtils.checkContains(this , ConstantsUtils.FIRST_TIME_BIO)){
            PreferenceUtils.insertData(this, ConstantsUtils.FIRST_TIME_BIO , ConstantsUtils.FIRST_TIME_BIO_VALUE)  ;
        }else {
            PreferenceUtils.insertData(this, ConstantsUtils.FIRST_TIME_BIO , ConstantsUtils.FIRST_SEC_BIO_VALUE)  ;
        }
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        valueForReg = intent.getStringExtra(ConstantsUtils.ForRegister);

        if (valueForReg.equalsIgnoreCase(ConstantsUtils.provider)) {
            binding.signInForWhom.setText(ConstantsUtils.For_Provider);
        } else {
            binding.signInForWhom.setText(ConstantsUtils.For_Patient);
        }
        mApiService = ApiUtils.getApiService();
    }

    private void setListeners() {
        if(PreferenceUtils.retriveData(LoginActivity.this ,
                ConstantsUtils.BIO_METRIC_APPROVE).equalsIgnoreCase(ConstantsUtils.BIO_METRIC_APPROVE_YES)){
            binding.tvBiometric.setVisibility(View.VISIBLE);
            binding.editEmail.setText("userxxxxx");
        }else {
            if(PreferenceUtils.checkContains(this , ConstantsUtils.USER_LOGOUT)){
                if(PreferenceUtils.retriveData(this, ConstantsUtils.USER_LOGOUT).equalsIgnoreCase(ConstantsUtils.LOGOUT)){
                    binding.editEmail.setText("userxxxxx");
                }
            }
        }


        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PreferenceUtils.retriveData(LoginActivity.this ,
                        ConstantsUtils.FIRST_TIME_BIO).equalsIgnoreCase("1")){
                    showAlertDialog();
                }else  if(PreferenceUtils.retriveData(LoginActivity.this ,
                        ConstantsUtils.FIRST_TIME_BIO).equalsIgnoreCase("2")){
                     if(PreferenceUtils.retriveData(LoginActivity.this ,
                             ConstantsUtils.BIO_METRIC_APPROVE).equalsIgnoreCase(ConstantsUtils.BIO_METRIC_APPROVE_NO)){
                        showAlertDialog();
                     }else {
                         startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class)
                                 .putExtra(ConstantsUtils.USER_TYPE,
                                         valueForReg));
                         finish();
                         PreferenceUtils.insertData(LoginActivity.this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_YES);
                     }
                }


            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setTitle("Thrucare");
        alertDialogBuilder.setMessage("Do you want to login using Face id/Touch id?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        PreferenceUtils.insertData(LoginActivity.this ,
                                ConstantsUtils.BIO_METRIC_APPROVE  , ConstantsUtils.BIO_METRIC_APPROVE_YES);
                        startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class)
                                .putExtra(ConstantsUtils.USER_TYPE,
                                        valueForReg));
                        finish();
                        PreferenceUtils.insertData(LoginActivity.this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_YES);

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtils.insertData(LoginActivity.this ,
                        ConstantsUtils.BIO_METRIC_APPROVE  , ConstantsUtils.BIO_METRIC_APPROVE_NO);
                startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class)
                        .putExtra(ConstantsUtils.USER_TYPE,
                                valueForReg));
                finish();
                PreferenceUtils.insertData(LoginActivity.this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_YES);

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void checkBioMetricValidationOnDevice() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("biometricAvail", "can Authenticate");
                //if biometric available then authenticate
                showBioMetricPrompt();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.d("biometricAvail", "currantly unavailable");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.d("biometricAvail", "Not unavailable on device");
                BaseUtils.showToast(this, "Your phone doesn't support this");
                break;
           /* case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;*/
        }
    }

    private void showBioMetricPrompt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt(LoginActivity.this,
                    executer, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    BaseUtils.showToast(LoginActivity.this, "Something's wrong");
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class)
                            .putExtra(ConstantsUtils.USER_TYPE,
                                    valueForReg));
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    BaseUtils.showToast(LoginActivity.this, "Authentication Failed");
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Please authenticatie using your biometric credentials")
                    // this method is not showing ->  .setAllowedAuthenticators()
                    // setDeviceCredentialAllowed is deprecated so have to use above method
                    .setNegativeButtonText("No Thanks")
                    .build();

            binding.tvBiometric.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biometricPrompt.authenticate(promptInfo);
                }
            });


        }
    }

    private void signIn() {
        JsonObject jsonObject = new JsonObject();

        try {
            jsonObject.addProperty(getResources().getString(R.string.username), String.valueOf(binding.editEmail.getText()));
            jsonObject.addProperty(getResources().getString(R.string.password_1), String.valueOf(binding.editPassword.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<LoginResponse> call = mApiService.signIn(getResources().getString(R.string.application_json), jsonObject);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {

                if (response.code() == 201) {
                    startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("BankSelection", "onResponse: " + t);
            }
        });

    }
}