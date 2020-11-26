package com.thrucare.healthcare.activity.patient.bookAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivitySchedulAppointmentBinding;
import com.thrucare.healthcare.pojo.AvailableSchedule;
import com.thrucare.healthcare.pojo.AvailableSlotTime;
import com.thrucare.healthcare.pojo.TimeSlotModel;
import com.thrucare.healthcare.retrofit.ApiInterface;
import com.thrucare.healthcare.retrofit.ApiUtils;
import com.thrucare.healthcare.utils.BaseUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class ScheduleAppointmentActivity extends AppCompatActivity {

    private ActivitySchedulAppointmentBinding binding;
    private ApiInterface mApiService;
    private List<AvailableSchedule.Slot> slots;
    private TimeSlotOneAdapter customAdapter;
    List<TimeSlotModel> listSlotTime  = new ArrayList<>();
    private int previousPosition  = -1;
    private int current  = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySchedulAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Book an appointment");
        setContentView(view);



        // Create an object of CustomAdapter and set Adapter to GirdView

        mApiService = ApiUtils.getApiService();
        getAvailableScheduleApiCalling();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH)+1;
        String month  = getMonthStr(month1);
        binding.tvMonthYear.setText(month+" "+year);

        setContentView(view);

        binding.calendraView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();
                if(day==6||day==18){
                    binding.llnTimeSlot.setVisibility(View.VISIBLE);

                    getSlotTimeApiCalling();

                }else {
                    binding.llnTimeSlot.setVisibility(View.INVISIBLE);
                }
            }
        });


        binding.calendraView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                String monthStr = getMonthStr(month);
                binding.tvMonthYear.setText(monthStr + " " + year);
            }
        });

        binding.btnScheduleProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleAppointmentActivity.this, ConfirmAppointmentActivity.class));
            }
        });


        binding.gridViewTimeSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("position" , String.valueOf(position));
                 if(previousPosition==-1){
                     listSlotTime.get(position).setIsSelection(1);
                     customAdapter.notifyDataSetChanged();
                     previousPosition  = position;
                 }else {
                     listSlotTime.get(previousPosition).setIsSelection(0);
                     listSlotTime.get(position).setIsSelection(1);
                     previousPosition  = position;
                     customAdapter.notifyDataSetChanged();
                 }

            }
        });

    }

    private void getSlotTimeApiCalling() {

        Call<AvailableSlotTime.ResponseSlotTime> call  = mApiService.getTimeSlotOfAvailableDate();

        call.enqueue(new Callback<AvailableSlotTime.ResponseSlotTime>() {
            @Override
            public void onResponse(Call<AvailableSlotTime.ResponseSlotTime> call,
                                   Response<AvailableSlotTime.ResponseSlotTime> response) {
                 listSlotTime.clear();
                if(response.code()==200||response.code()==201){
                    List<AvailableSlotTime.Item>  itemList  = response.body().getItems();
                    for(int i =0 ; i<itemList.size(); i++){
                        String slotValue  = itemList.get(i).getStart().substring(11, 16);
                        String status  = itemList.get(i).getStatus();
                        boolean overbooked  = itemList.get(i).getOverbooked();
                              listSlotTime.add(new TimeSlotModel(slotValue , 0  , status , overbooked));
                    }
                }
                             customAdapter = new TimeSlotOneAdapter(getApplicationContext(), listSlotTime);
                binding.gridViewTimeSlot.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<AvailableSlotTime.ResponseSlotTime> call, Throwable t) {

            }
        });
    }

    private void getAvailableScheduleApiCalling() {
        Call<AvailableSchedule.ResponseScheduleAvailable> call  = mApiService.getAvailableAppointment();
        call.enqueue(new Callback<AvailableSchedule.ResponseScheduleAvailable>() {
            @Override
            public void onResponse(Call<AvailableSchedule.ResponseScheduleAvailable> call,
                                   Response<AvailableSchedule.ResponseScheduleAvailable> response) {

                if(response.code()==200||response.code()==201){
                    BaseUtils.showToast(ScheduleAppointmentActivity.this, "Success Available schedule ");
                    List<String> slotList  = new ArrayList<>();
                    slots  = response.body().getItems().get(0).getSlots();
                    for(int i =0 ; i<slots.size(); i++){
                       String  slot  = slots.get(i).getStart().substring(0, 10);
                       slotList.add(slot);
                       Log.d("slot", slot);
                    }

                    if(slotList.size()>0){
                        setMarksDateOnCal(slotList);
                    }

                }
            }

            @Override
            public void onFailure(Call<AvailableSchedule.ResponseScheduleAvailable> call, Throwable t) {

            }
        });
    }

    private void setMarksDateOnCal(List<String> slotList) {
        for(int i =0 ; i<slotList.size() ; i++){
            String date   = slotList.get(i);
            String parts1[] = date.split("-");

            int year = Integer.parseInt(parts1[0]);
            int month , day;
            if(parts1[1].charAt(0)==0){
                 month = Integer.parseInt(parts1[1].substring(1));
            }else {
                 month = Integer.parseInt(parts1[1]);
            }
            if(parts1[2].charAt(0)==0){
                day = Integer.parseInt(parts1[2].substring(1));
            }else {
                day = Integer.parseInt(parts1[2]);
            }

            binding.calendraView.markDate(year, month, day);
           // Log.d("slotssss", ""+year+month+day);
        }
    }

    private String getMonthStr(int month) {
        if (month == 1) {
            return "Jan";
        } else if (month == 2) {
            return "Fab";
        } else if (month == 3) {
            return "Mar";
        } else if (month == 4) {
            return "April";
        } else if (month == 5) {
            return "May";
        } else if (month == 6) {
            return "June";
        } else if (month == 7) {
            return "July";
        } else if (month == 8) {
            return "Aug";
        } else if (month == 9) {
            return "Sep";
        } else if (month == 10) {
            return "Oct";
        } else if (month == 11) {
            return "Nov";
        } else {
            return "Dec";
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