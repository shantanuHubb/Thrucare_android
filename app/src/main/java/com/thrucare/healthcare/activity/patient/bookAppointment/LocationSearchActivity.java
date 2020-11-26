package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.databinding.ActivityLocationScreenBinding;
import com.thrucare.healthcare.databinding.ActivityLocationSearchBinding;

public class LocationSearchActivity extends AppCompatActivity {

    private ActivityLocationSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

    }
}