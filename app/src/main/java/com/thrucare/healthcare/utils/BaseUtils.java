package com.thrucare.healthcare.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

public class BaseUtils {

    public static void showToast (Activity activity , String msg){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 20);
                toast.show();

            }
        });

    }
}
