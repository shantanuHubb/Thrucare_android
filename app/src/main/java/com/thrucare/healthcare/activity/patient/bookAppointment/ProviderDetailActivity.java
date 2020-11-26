package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.providerReview.ProviderReviewActivity;
import com.thrucare.healthcare.databinding.ActivityProviderDetailBinding;
import com.thrucare.healthcare.utils.PreferenceUtils;

public class ProviderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProviderDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProviderDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        // inti view
        intiView();

    }

    private void intiView() {
        binding.tvBookAppointment.setOnClickListener(this);
        binding.tvReviews.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_book_appointment:
                PreferenceUtils.insertData(this , "BOOK"  , "Schedule");
                startActivity(new Intent(this, StepOneAppointmentActivity.class));
                break;
            case R.id.tv_reviews:
                startActivity(new Intent(this, ProviderReviewActivity.class));
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