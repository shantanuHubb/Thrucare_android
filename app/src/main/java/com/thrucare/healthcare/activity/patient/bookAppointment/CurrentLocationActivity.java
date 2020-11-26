package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.home.HomeFragment;
import com.thrucare.healthcare.databinding.ActivityCurrentLocationBinding;

public class CurrentLocationActivity extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {

    private GoogleMap mMap;
    String lat, longi, location;
    private ActivityCurrentLocationBinding binding;
    private AutoDetectListener autoDetectListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentLocationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        this.autoDetectListener  = (AutoDetectListener) HomeFragment.getInstance();

        lat  = getIntent().getStringExtra("lat");
        longi  = getIntent().getStringExtra("longi");
        location  = getIntent().getStringExtra("location");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setListener();
    }

    private void setListener() {
        binding.btnSaveLocation.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));
        mMap.addMarker(new MarkerOptions().position(sydney).title(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_save_location:
                autoDetectListener.onClick(location, Double.parseDouble(lat) , Double.parseDouble(lat));
                super.onBackPressed();
                break;
        }
    }


    public interface AutoDetectListener{
        void onClick(String location , double lat , double longt);
    }
}