package com.thrucare.healthcare.activity.patient.myAppointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.bookAppointment.ConfirmAppointmentActivity;
import com.thrucare.healthcare.activity.patient.bookAppointment.StepOneAppointmentActivity;
import com.thrucare.healthcare.activity.patient.patientQuestionnaire.QuestionnaireActivity;
import com.thrucare.healthcare.activity.patient.payForBookingAppointtment.ConfirmAppointmentCheckOutActivity;
import com.thrucare.healthcare.awsChime.VideoActivity;
import com.thrucare.healthcare.pojo.ConfirmPaymentResponse;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;
import com.thrucare.healthcare.utils.ConstantsUtils;
import com.thrucare.healthcare.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    Context context;
    private ApiInterface mApiInterface;
    View v;
    SetOnCancelAppointment setOnCancelAppointment;
    MyAppointmentFragment myAppointmentFragment;

    public CustomBottomSheet(Context context, MyAppointmentFragment appointmentFragment) {
        this.context = context;
        this.setOnCancelAppointment  = (SetOnCancelAppointment) appointmentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.custom_bottom_sheet,
                container, false);


        v.findViewById(R.id.tv_questionnaire).setOnClickListener(this);
        v.findViewById(R.id.tv_pay_appointment_booking).setOnClickListener(this);
        v.findViewById(R.id.tv_cancel_appointment).setOnClickListener(this);
        v.findViewById(R.id.tv_reschedule_appointment).setOnClickListener(this);
        v.findViewById(R.id.tv_appointment_connect).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_questionnaire:
                startActivity(new Intent(context, QuestionnaireActivity.class));
                break;
            case R.id.tv_pay_appointment_booking:
                getPaymentData();
                break;
            case  R.id.tv_cancel_appointment:
                cancelAppointmentApiCalling();
                break;

            case  R.id.tv_reschedule_appointment:
                startActivity(new Intent(context, StepOneAppointmentActivity.class));
                PreferenceUtils.insertData(context , "BOOK"  , "ReSchedule");
                break;

            case R.id.tv_appointment_connect:
               // startActivity(new Intent(context, VideoActivity.class));
                break;
        }
    }

    private void cancelAppointmentApiCalling() {
        mApiInterface = ApiUtils.getApiService();
        Call<ResponseBody> call  = mApiInterface.cancelAppointment();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    BaseUtils.showToast(getActivity() , "Cancel Appointment successfully ");
                    setOnCancelAppointment.onCancel();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getPaymentData() {
        mApiInterface = ApiUtils.getApiService();
        Call<ConfirmPaymentResponse> call = mApiInterface.getPaymentData();
        call.enqueue(new Callback<ConfirmPaymentResponse>() {
            @Override
            public void onResponse(Call<ConfirmPaymentResponse> call, Response<ConfirmPaymentResponse> response) {
                ConfirmPaymentResponse obj = response.body();

                String remoteDate = obj.getStart().replace("T", " ").replace(".000Z", "");
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = dt.parse(remoteDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dt2 = new SimpleDateFormat("hh:mm");


                String name = obj.getPatientName();
                String status = obj.getStatus();
                String amount = obj.getAmount().toString();
                String contact = obj.getPatient().getPhone();
                String location = obj.getLocation().getAddress().getCountry();
                String serviceType = obj.getServiceType().get(0).getDisplay();
                String speciality = obj.getSpeciality().get(0).getDisplay();
                String dateToDisplay = dt1.format(date);
                String timeToDisplay = dt2.format(date);

                startActivity(new Intent(context, ConfirmAppointmentCheckOutActivity.class)
                        .putExtra("name", name)
                        .putExtra("status",status)
                        .putExtra("amount",amount)
                        .putExtra("contact",contact)
                        .putExtra("location",location)
                        .putExtra("serviceType",serviceType)
                        .putExtra("speciality",speciality)
                        .putExtra("dateToDisplay",dateToDisplay)
                        .putExtra("timeToDisplay",timeToDisplay)
                );
            }

            @Override
            public void onFailure(Call<ConfirmPaymentResponse> call, Throwable t) {
                BaseUtils.showToast(getActivity(),t.getMessage());
            }
        });
    }

  public   interface SetOnCancelAppointment{
        void onCancel();
  }
}
