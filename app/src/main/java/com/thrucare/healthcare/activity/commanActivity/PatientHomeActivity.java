package com.thrucare.healthcare.activity.commanActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.profile.ProfileInfoActivity;
import com.thrucare.healthcare.pojo.ProfileResponse;
import com.thrucare.healthcare.pojo.modelClasses.ProfileDataResponse;
import com.thrucare.healthcare.pojo.modelClasses.ProfilePatientResponse;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.retrofit.PatientApi.PatientUtils;
import com.thrucare.healthcare.retrofit.RealApi.ProviderUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientHomeActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    static Context activity;
    private ApiInterface mAPiService;
    private ApiInterface ProviderServiceApi;
    private ApiInterface PatientServiceApi;
    private String userType;
    private DrawerLayout drawer;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAPiService = ApiUtils.getApiService();
        ProviderServiceApi = ProviderUtils.getService();
        PatientServiceApi = PatientUtils.getPatientService();
        PreferenceUtils.insertData(PatientHomeActivity.this , ConstantsUtils.USER_LOGOUT  , ConstantsUtils.LOGOUT);

        activity = getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        userType = getIntent().getStringExtra(ConstantsUtils.USER_TYPE);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_diagnosis,
                R.id.nav_medication, R.id.nav_allergies, R.id.nav_family_history,
                R.id.nav_social_history, R.id.nav_provider_appointments, R.id.nav_order,
                R.id.nav_medical_report , R.id.nav_dependents , R.id.nav_immunization , R.id.nav_provider_staff)
                .setDrawerLayout(drawer)
                .build();

        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        hideItem();
       // navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView profileName = (TextView) headerView.findViewById(R.id.tv_profile_name);
        profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userType.equals("provider")){
                    //getProfileDataForProvider();
                     getProfileDataProvider();
                }else {
                    getProfileDataPatient();
                }
            }
        });
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PatientHomeActivity.this);
                alertDialogBuilder.setTitle("Thrucare?");
                alertDialogBuilder.setMessage("Are you sure want to  logout ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                PreferenceUtils.insertData(PatientHomeActivity.this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_NO);
                                PreferenceUtils.insertData(PatientHomeActivity.this , ConstantsUtils.USER_LOGOUT  , ConstantsUtils.LOGOUT);
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                startActivity(new Intent(PatientHomeActivity.this , OnBoardingActivity.class)
                                        . putExtra(ConstantsUtils.SHOW_VALUE, getResources().getString(R.string.Login)));
                                finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });

    }

    private void getProfileDataPatient() {
        Call<ProfilePatientResponse> call = PatientServiceApi.getPatientProfile("application/json" , PreferenceUtils.retriveData(this , "PUUID_PATIENT") , ConstantsUtils.API_KEY);
        call.enqueue(new Callback<ProfilePatientResponse>() {
            @Override
            public void onResponse(Call<ProfilePatientResponse> call, Response<ProfilePatientResponse> response) {
                BaseUtils.showToast(PatientHomeActivity.this , "success");
                ProfilePatientResponse obj = response.body();
                String name = obj.getFirstName() + " " + obj.getMiddleName() + " " + obj.getLastName();
                String address = obj.getAddress().getLine1() + " " + obj.getAddress().getLine2() + " " + obj.getAddress().getCity() + " " + obj.getAddress().getState() + " " + obj.getAddress().getCountry();
                String remoteDate = obj.getBirthDate().replace("T", " ").replace(".000Z", " ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                Date date = null;
                try {
                    date = sdf.parse(remoteDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
                String dateToDisplay = dt1.format(date);


                startActivity(new Intent(PatientHomeActivity.this, ProfileInfoActivity.class)

                        .putExtra(getResources().getString(R.string.name), name)
                        .putExtra(getResources().getString(R.string.phone), obj.getPhone())
                        .putExtra("email", obj.getEmail())
                        .putExtra(getResources().getString(R.string.dob), dateToDisplay)
                        .putExtra("gender",obj.getGender().getDisplay())
                        .putExtra(getResources().getString(R.string.identifier),obj.getIdentifier().get(0).getValue())
                        .putExtra(getResources().getString(R.string.address),address)
                        .putExtra(getResources().getString(R.string.blood_group),obj.getBloodGroup().getDisplay())
                        .putExtra(ConstantsUtils.USER_TYPE,ConstantsUtils.patient)
                );


            }

            @Override
            public void onFailure(Call<ProfilePatientResponse> call, Throwable t) {
                BaseUtils.showToast(PatientHomeActivity.this , "failed ");
            }
        });
    }


//    private void getProfileDataForPatient() {
//        Call<ProfileResponse> call = mAPiService.getPatientProfileData();
//        call.enqueue(new Callback<ProfileResponse>() {
//            @Override
//            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//
//                ProfileResponse obj = response.body();
//                String name = obj.getFirstName() + " " + obj.getMiddleName() + " " + obj.getLastName();
//                String remoteDate = obj.getBirthDate().
//                        replace("T", " ").replace(".000Z", "");
//
//                //  language,adress,emergency contact,blood group not in api
//
//                startActivity(new Intent(
//                        PatientHomeActivity.this, ProfileInfoActivity.class)
//
//                        .putExtra("name", name)
//                        .putExtra("phone", obj.getPhone())
//                        .putExtra("email", obj.getEmail())
//                        .putExtra("dob", obj.getBirthDate())
//                        .putExtra("gender",obj.getGender().getDisplay())
//                        .putExtra("identifier",obj.getIdentifier().get(0).getValue().toString())
//                        .putExtra("insurance",obj.getInsurances().getItems().get(0).getPayer().getDisplay())
//                        .putExtra(ConstantsUtils.USER_TYPE,ConstantsUtils.patient)
//
//
//                );
//            }
//
//            @Override
//            public void onFailure(Call<ProfileResponse> call, Throwable t) {
//
//            }
//        });
//    }

//    private void getProfileDataForProvider() {
//        Call<ProfileResponse> call = mAPiService.getProviderProfileData();
//        call.enqueue(new Callback<ProfileResponse>() {
//            @Override
//            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                Log.d("test", response.toString());
//                ProfileResponse obj = response.body();
//                String name = obj.getFirstName() + " " + obj.getMiddleName() + " " + obj.getLastName();
//
//
//                String remoteDate = obj.getBirthDate().replace("T", " ").replace(".000Z", "");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//                Date date = null;
//                try {
//                    date = sdf.parse(remoteDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
//                String dateToDisplay = dt1.format(date);
//
//                //  language,adress,emergency contact,blood group not in api
//
//                startActivity(new Intent(PatientHomeActivity.this, ProfileInfoActivity.class)
//
//                        .putExtra("name", name)
//                        .putExtra("phone", obj.getPhone())
//                        .putExtra("email", obj.getEmail())
//                        .putExtra("dob", dateToDisplay)
//                        .putExtra("gender",obj.getGender().getDisplay())
//                        .putExtra("identifier",obj.getIdentifier().get(0).getValue().toString())
//                        .putExtra("insurance",obj.getInsurances().getItems().get(0).getPayer().getDisplay())
//                        .putExtra(ConstantsUtils.USER_TYPE,ConstantsUtils.provider)
//
//                );
//            }
//
//            @Override
//            public void onFailure(Call<ProfileResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void getProfileDataProvider()
    {
   Call<ProfileDataResponse.UserProfile> call = ProviderServiceApi.getProviderProfile("application/json" , PreferenceUtils.retriveData(this , "PUUID") , ConstantsUtils.API_KEY);
            call.enqueue(new Callback<ProfileDataResponse.UserProfile>() {
                @Override
                public void onResponse(Call<ProfileDataResponse.UserProfile> call, Response<ProfileDataResponse.UserProfile> response) {

                    ProfileDataResponse.UserProfile obj = response.body();
                    String name = obj.getFirstName() + " " + obj.getMiddleName() + " " + obj.getLastName();
                    String remoteDate = obj.getBirthDate().replace("T", " ").replace(".000Z", " ");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    Date date = null;
                    try {
                        date = sdf.parse(remoteDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
                      String dateToDisplay = dt1.format(date);

                    //  language,address ,emergency contact,blood group not in api

                    startActivity(new Intent(PatientHomeActivity.this, ProfileInfoActivity.class)

                            .putExtra(getResources().getString(R.string.name), name)
                            .putExtra(getResources().getString(R.string.phone), obj.getPhone())
                            .putExtra("email", obj.getEmail())
                            .putExtra(getResources().getString(R.string.dob), dateToDisplay)
                            .putExtra("gender",obj.getGender().getDisplay())
                            .putExtra(ConstantsUtils.USER_TYPE,ConstantsUtils.provider)
                    );
                }

                @Override
                public void onFailure(Call<ProfileDataResponse.UserProfile> call, Throwable t) {
                    BaseUtils.showToast(PatientHomeActivity.this , "failed error");
                }
            });







    }
    public static Context getInstance() {
        return activity;
    }

    private void hideItem() {
        String userType = getIntent().getStringExtra(ConstantsUtils.USER_TYPE);
        if (userType.equalsIgnoreCase(ConstantsUtils.patient)) {
            navigationView = findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_provider_appointments).setVisible(false);
            nav_Menu.findItem(R.id.nav_provider_availability).setVisible(false);
            nav_Menu.findItem(R.id.nav_provider_profile).setVisible(false);
            nav_Menu.findItem(R.id.nav_provider_service).setVisible(false);
            nav_Menu.findItem(R.id.nav_provider_setting).setVisible(false);
            nav_Menu.findItem(R.id.nav_provider_staff).setVisible(false);
            PreferenceUtils.insertData(this  , ConstantsUtils.USER_TYPE  ,userType);
        } else {
            navigationView = findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_gallery).setVisible(false);
            nav_Menu.findItem(R.id.nav_diagnosis).setVisible(false);
            nav_Menu.findItem(R.id.nav_medication).setVisible(false);
            nav_Menu.findItem(R.id.nav_allergies).setVisible(false);
            nav_Menu.findItem(R.id.nav_social_history).setVisible(false);
            nav_Menu.findItem(R.id.nav_family_history).setVisible(false);
            nav_Menu.findItem(R.id.nav_medical_report).setVisible(false);
            nav_Menu.findItem(R.id.nav_order).setVisible(false);
            nav_Menu.findItem(R.id.nav_dependents).setVisible(false);
            nav_Menu.findItem(R.id.nav_preference).setVisible(false);
            nav_Menu.findItem(R.id.nav_immunization).setVisible(false);
            PreferenceUtils.insertData(this  , ConstantsUtils.USER_TYPE  , userType);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Thrucare?");
                alertDialogBuilder.setMessage("Are you sure want to  logout ?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        PreferenceUtils.insertData(PatientHomeActivity.this , ConstantsUtils.USER_LOGIN  , ConstantsUtils.USER_LOGIN_NO);
                                        PreferenceUtils.insertData(PatientHomeActivity.this , ConstantsUtils.USER_LOGOUT  , ConstantsUtils.LOGOUT);
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                        startActivity(new Intent(PatientHomeActivity.this , OnBoardingActivity.class)
                                                . putExtra(ConstantsUtils.SHOW_VALUE, getResources().getString(R.string.Login)));
                                        finish();

                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!!");
        alertDialogBuilder.setMessage("Are you sure want to exit ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
