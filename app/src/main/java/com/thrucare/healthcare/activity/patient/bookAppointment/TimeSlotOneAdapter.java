package com.thrucare.healthcare.activity.patient.bookAppointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.TimeSlotModel;

import java.util.List;

public class TimeSlotOneAdapter extends BaseAdapter {

    Context ctx;
    List<TimeSlotModel> listList;


    public TimeSlotOneAdapter(Context applicationContext, List<TimeSlotModel> listSlotTime) {
        this.ctx = applicationContext;
        this.listList  = listSlotTime;
    }

    @Override
    public int getCount() {
        return listList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflter = (LayoutInflater.from(ctx));
        view = inflter.inflate(R.layout.time_slot_item, null); // inflate the layout
        TextView textView  = view.findViewById(R.id.tv_slot_time);
        textView.setText(listList.get(i).value);
        if(listList.get(i).isSelection==0){
            textView.setBackgroundColor(ctx.getResources().getColor(R.color.white));
            textView.setTextColor(ctx.getResources().getColor(R.color.black));
            if(!listList.get(i).isOverBook && !listList.get(i).status.equalsIgnoreCase("free")){
                textView.setBackgroundColor(ctx.getResources().getColor(R.color.orange));
                textView.setTextColor(ctx.getResources().getColor(R.color.white));
            }
        }else{
                textView.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
                textView.setTextColor(ctx.getResources().getColor(R.color.white));

        }


        return view;
    }

}
