package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.allergies.AllergiesFragment;
import com.thrucare.healthcare.activity.patient.diagnosis.DiagnosisFragment;
import com.thrucare.healthcare.activity.patient.immunization.ImmunizationFragment;
import com.thrucare.healthcare.activity.patient.medicalReport.MedicalReportFragment;
import com.thrucare.healthcare.activity.patient.medications.MedicationFragment;
import com.thrucare.healthcare.activity.patient.order.OrdersFragment;
import com.thrucare.healthcare.databinding.ActivityPatientInfoBinding;
import com.thrucare.healthcare.utils.ConstantsUtils;

public class PatientInfoActivity extends AppCompatActivity {

    private ActivityPatientInfoBinding binding;
    private int isBack  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        binding.cardDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new DiagnosisFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            }
        });

        binding.cardMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new MedicationFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            }
        });


        binding.cardAllergiesReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new AllergiesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                //startActivity(new Intent(PatientInfoActivity.this, AllergiesReportProviderActivity.class));
            }
        });


        binding.cardOrderProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new OrdersFragment(ConstantsUtils.For_Provider);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment,
                                fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            }
        });

        binding.cardMedicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new MedicalReportFragment(ConstantsUtils.For_Provider);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment,
                                fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });


        binding.cardImmunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.idContainer.setVisibility(View.VISIBLE);
                Fragment fragment = new ImmunizationFragment(ConstantsUtils.For_Provider);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_container, fragment,
                                fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.idContainer.setVisibility(View.GONE);
                isBack++;
                if(isBack>=1){
                    super.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.idContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        binding.idContainer.setVisibility(View.GONE);
        isBack++;
        if(isBack>=1){
            super.onBackPressed();
        }
    }
}