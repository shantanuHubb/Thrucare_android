package com.thrucare.healthcare.activity.patient.payForBookingAppointtment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.ConfirmAppontmentProviderActivity;
import com.thrucare.healthcare.databinding.ActivityAddFamilyHistoryBinding;
import com.thrucare.healthcare.databinding.ActivityConfirmAppointmentCheckOutBinding;

public class ConfirmAppointmentCheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityConfirmAppointmentCheckOutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmAppointmentCheckOutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        binding.tvCheckoutName.setText(getIntent().getStringExtra("name"));
        binding.tvCheckoutStatus.setText(getIntent().getStringExtra("status"));
        binding.tvCheckoutAmount.setText(getIntent().getStringExtra("amount"));
        binding.tvCheckoutContact.setText(getIntent().getStringExtra("contact"));
        binding.tvCheckoutLocation.setText(getIntent().getStringExtra("location"));
        binding.tvCheckoutServiceType.setText(getIntent().getStringExtra("serviceType"));
        binding.tvCheckoutSpeciality.setText(getIntent().getStringExtra("speciality"));
        binding.tvCheckoutDate.setText(getIntent().getStringExtra("dateToDisplay"));
        binding.tvCheckoutTime.setText(getIntent().getStringExtra("timeToDisplay"));

        //  inti view
        intiView();
    }

    private void intiView() {
        binding.btnPayForAppointment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pay_for_appointment:
                AddCardDetailBottomSheetFragment bottomSheet = new AddCardDetailBottomSheetFragment(this);
                bottomSheet.show(getSupportFragmentManager(), "Model");
                break;
        }
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