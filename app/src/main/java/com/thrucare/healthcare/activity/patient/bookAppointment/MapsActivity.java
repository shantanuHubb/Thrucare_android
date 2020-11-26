package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.home.HomeFragment;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText edtSearch;
    SearchLocationListener searchLocationListener;
    Address address ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.searchLocationListener  = (SearchLocationListener) HomeFragment.getInstance();
        edtSearch  = findViewById(R.id.edt_location_provider);
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try{
                    if(s.length()>=3){
                        searchLocation(edtSearch.getText().toString().trim());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        findViewById(R.id.btn_search_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtSearch.getText().toString().length()>0){
                    searchLocationListener.onClickSearch(edtSearch.getText().toString()
                            , address.getLatitude(), address.getLongitude());

                    onBackPressed();
                }
            }
        });
    }

    private void searchLocation(String location) {
        mMap.clear();
        if(mMap != null) {
            List<Address> addressList;

            if (location != null || !location.equals("")) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);

                    if(addressList.size()>0){
                        address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            MapsInitializer.initialize(this);
            searchLocation(location);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public interface SearchLocationListener{
       void onClickSearch(String location , double lat , double longt);
    }
}