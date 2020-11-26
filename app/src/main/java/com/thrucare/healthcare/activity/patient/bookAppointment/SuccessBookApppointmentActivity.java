package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.thrucare.healthcare.databinding.ActivityBookAppointmentBinding;
import com.thrucare.healthcare.databinding.ActivitySuccessBookApppointmentBinding;

public class SuccessBookApppointmentActivity extends AppCompatActivity {

    private ActivitySuccessBookApppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBookApppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        setContentView(view);
    }
}