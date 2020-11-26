package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityLocationScreenBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationScreenActivity extends AppCompatActivity  implements View.OnClickListener{


    private ActivityLocationScreenBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);


        intiView();
        // registering the with google to using the auto
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void intiView() {
        binding.cardAutoDetectionLocation.setOnClickListener(this);
        binding.imgLocationMapSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.card_auto_detection_location:

                if (ActivityCompat.checkSelfPermission(LocationScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                        checkSelfPermission(LocationScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LocationScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }else{
                    // Write you code here if permission already given.
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        Log.d("location", String.valueOf(location.getLatitude()));
                                        double lat = location.getLatitude();
                                        double longt  = location.getLongitude();
                                        getAddress(lat, longt);
                                    }
                                }
                            });
                }
                break;


            case R.id.img_location_map_search:
                startActivity(new Intent(this, MapsActivity.class));
                finish();
                break;
        }
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(LocationScreenActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address obj = addresses.get(0);
        String add = obj.getAddressLine(0);
        add = add + "\n" + obj.getCountryName();
        add = add + "\n" + obj.getCountryCode();
        add = add + "\n" + obj.getAdminArea();
        add = add + "\n" + obj.getPostalCode();
        add = add + "\n" + obj.getSubAdminArea();
        add = add + "\n" + obj.getLocality();
        add = add + "\n" + obj.getSubThoroughfare();

        Log.v("IGA", "Address  "+obj.getSubAdminArea());

     // autoDetectListener.onClick(obj.getSubAdminArea() , lat , lat);

        startActivity(new Intent(this, CurrentLocationActivity.class).
                putExtra("lat", String.valueOf(lat)).putExtra("longi" ,
                String.valueOf(lng)).putExtra("location" ,obj.getSubAdminArea() ));
        finish();
    //  super.onBackPressed();
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