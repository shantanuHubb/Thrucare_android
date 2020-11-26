package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.activity.patient.bookAppointment.ConfirmAppointmentActivity;
import com.thrucare.healthcare.databinding.ActivityConfirmAppontmentProviderBinding;
import com.thrucare.healthcare.pojo.ProviderApointmentConfirm;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmAppontmentProviderActivity extends AppCompatActivity {

    private ActivityConfirmAppontmentProviderBinding binding;
    private ApiInterface mApiInterface;
    public static ConfirmAppontmentProviderActivity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmAppontmentProviderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getAppointmentDetails();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setContentView(view);
        binding.btnConfirmAppointmentProviderPrePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtils.showToast(ConfirmAppontmentProviderActivity.this, "Success");
            }
        });

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

    private void getAppointmentDetails() {
        binding.progressConfirmAppointmentProviderLocation.setVisibility(View.VISIBLE);
        mApiInterface = ApiUtils.getApiService();
        Call<ProviderApointmentConfirm> call = mApiInterface.getAppointmentDetailsForConformation();

        call.enqueue(new Callback<ProviderApointmentConfirm>() {

            @Override
            public void onResponse(Call<ProviderApointmentConfirm> call, Response<ProviderApointmentConfirm> response) {
                binding.progressConfirmAppointmentProviderLocation.setVisibility(View.GONE);
                ProviderApointmentConfirm Obj = response.body();

                binding.tvConfirmAppointmentProviderLocation.setText(Obj.getLocation().getAddress().getCity());
                binding.tvConfirmAppointmentProviderServiceType.setText(Obj.getServiceType().get(0).getDisplay());
                binding.tvConfirmAppointmentProviderSpeciality.setText(Obj.getSpeciality().get(0).getDisplay());

                String remoteDate = Obj.getStart().replace("T", " ").replace(".000Z", "");
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date date = dt.parse(remoteDate);
                    SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat dt2 = new SimpleDateFormat("hh:mm");
                    String dateToDisplay = dt1.format(date);
                    String timeToDisplay = dt2.format(date);

                    binding.tvConfirmAppointmentProviderDate.setText(dateToDisplay);
                    binding.tvConfirmAppointmentProviderTime.setText(timeToDisplay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                binding.tvConfirmAppointmentProviderPatentName.setText(Obj.getPatientName());
                binding.tvConfirmAppointmentProviderEmail.setText(Obj.getPatient().getEmail());
                binding.tvConfirmAppointmentProviderContactNo.setText(Obj.getPatient().getPhone());
                //not in api
                binding.tvConfirmAppointmentProviderPayer.setText("BlueCross BlueShield");
                binding.tvConfirmAppointmentProviderProduct.setText("PPO");
            }

            @Override
            public void onFailure(Call<ProviderApointmentConfirm> call, Throwable t) {

            }
        });




    }

}