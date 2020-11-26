package com.thrucare.healthcare.activity.commanActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityOnBoardingBinding;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.util.concurrent.Executor;

public class OnBoardingActivity extends AppCompatActivity {

    private ActivityOnBoardingBinding binding;
    private SharedPreferences preferences;

    private Executor executer;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        executer = ContextCompat.getMainExecutor(this);


        if(!PreferenceUtils.checkContains(this , ConstantsUtils.USER_LOGIN)){
            PreferenceUtils.insertData(this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_NO);
        }
         checkBioMetricValidationOnDevice();
        checkBioMetricApproval();

        checkIsUserLogin();
        if(!PreferenceUtils.checkContains(this,ConstantsUtils.FirstRunApp )){
            PreferenceUtils.insertData(this , ConstantsUtils.FirstRunApp , ConstantsUtils.FirstRunApp);
        }
        else  {
            PreferenceUtils.insertData(this , ConstantsUtils.FirstRunApp , ConstantsUtils.SEC_TIME_RUN);
            try{
               Intent intent = getIntent();
               String showValue  = intent.getStringExtra(ConstantsUtils.SHOW_VALUE);
               if(showValue.equalsIgnoreCase(getResources().getString(R.string.login))){
                   binding.tvLogin.setText(getResources().getString(R.string.Register));
               }else {
                   binding.tvLogin.setText(getResources().getString(R.string.Login));
               }

               binding.tvWithSignOrReg.setText(showValue);
           }catch (Exception w){
               w.printStackTrace();
           }
        }

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value  = binding.tvLogin.getText().toString();
                if(value.equalsIgnoreCase(getResources().getString(R.string.Login))){
                    startActivity(new Intent(OnBoardingActivity.this, OnBoardingActivity.class).
                            putExtra(ConstantsUtils.SHOW_VALUE, getResources().getString(R.string.Login)));
                    finish();
                }
                else {
                    startActivity(new Intent(OnBoardingActivity.this, OnBoardingActivity.class)
                            .putExtra(ConstantsUtils.SHOW_VALUE, getResources().getString(R.string.Register)));
                    finish();
                }
            }

        });


        binding.llnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvWithSignOrReg.getText().toString().equalsIgnoreCase(getResources().getString(R.string.Login))){
                    startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
                            .putExtra(ConstantsUtils.ForRegister, ConstantsUtils.patient));
                    finish();
                 PreferenceManager.getDefaultSharedPreferences(OnBoardingActivity.this).edit()
                         .putString(ConstantsUtils.ForRegister, ConstantsUtils.patient).apply();
                }else {
                    startActivity(new Intent(OnBoardingActivity.this, RegistrationActivity.class).
                            putExtra(ConstantsUtils.ForRegister, ConstantsUtils.patient));
                    finish();
                    PreferenceManager.getDefaultSharedPreferences(OnBoardingActivity.this).edit()
                            .putString(ConstantsUtils.ForRegister, ConstantsUtils.patient).apply();
                }

            }
        });

        binding.llnProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvWithSignOrReg.getText().toString().equalsIgnoreCase(getResources().getString(R.string.Login))){
                    startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
                            .putExtra(ConstantsUtils.ForRegister, ConstantsUtils.provider));
                    finish();
                    PreferenceManager.getDefaultSharedPreferences(OnBoardingActivity.this).
                            edit().putString(ConstantsUtils.ForRegister, ConstantsUtils.provider).apply();
                }
                else {
                    startActivity(new Intent(OnBoardingActivity.this, RegistrationActivity.class)
                            .putExtra(ConstantsUtils.ForRegister, ConstantsUtils.provider));
                    finish();
                    PreferenceManager.getDefaultSharedPreferences(OnBoardingActivity.this).edit().
                            putString(ConstantsUtils.ForRegister, ConstantsUtils.provider).apply();
                }
            }
        });
    }

    private void checkIsUserLogin() {
        if(PreferenceUtils.retriveData(this , ConstantsUtils.USER_LOGIN)
                .equalsIgnoreCase(ConstantsUtils.USER_LOGIN_YES)){
            if(PreferenceUtils.checkContains(this , ConstantsUtils.BIO_METRIC_APPROVE)){
                if(PreferenceUtils.retriveData(this , ConstantsUtils.BIO_METRIC_APPROVE)
                        .equalsIgnoreCase(ConstantsUtils.BIO_METRIC_APPROVE_YES)){
                    biometricPrompt.authenticate(promptInfo);
                }else {
                    startActivity(new Intent(OnBoardingActivity.this, PatientHomeActivity.class)
                            .putExtra(ConstantsUtils.USER_TYPE,
                                    PreferenceUtils.retriveData(this , ConstantsUtils.USER_TYPE)));
                }
            }

        }
    }

    private void checkBioMetricApproval() {

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
            biometricPrompt = new BiometricPrompt(OnBoardingActivity.this,
                    executer, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    BaseUtils.showToast(OnBoardingActivity.this, "Something's wrong");
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    startActivity(new Intent(OnBoardingActivity.this, PatientHomeActivity.class)
                            .putExtra(ConstantsUtils.USER_TYPE,
                                    PreferenceUtils.retriveData(OnBoardingActivity.this , ConstantsUtils.USER_TYPE)));
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    BaseUtils.showToast(OnBoardingActivity.this, "Authentication Failed");
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Please authenticatie using your biometric credentials")
                    // this method is not showing ->  .setAllowedAuthenticators()
                    // setDeviceCredentialAllowed is deprecated so have to use above method
                    .setNegativeButtonText("No Thanks")
                    .build();



        }
    }

}