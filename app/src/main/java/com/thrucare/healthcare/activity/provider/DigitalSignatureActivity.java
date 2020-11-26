package com.thrucare.healthcare.activity.provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityConfirmAppontmentProviderBinding;
import com.thrucare.healthcare.databinding.ActivityDigitalSigntureBinding;

import java.io.File;
import java.io.FileOutputStream;

public class DigitalSignatureActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE =1  ;
    private ActivityDigitalSigntureBinding binding;
    private String path;
    private GestureOverlayView gestureView;
    File file;
    private Bitmap bitmap;
    public boolean gestureTouch=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDigitalSigntureBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);
        Button donebutton = (Button) findViewById(R.id.DoneButton);
        LinearLayout clearButton =  findViewById(R.id.ClearButton);
        //clearButton.setText("Clear");

        //path= Environment.getExternalStorageDirectory()+"/signature.png";
       // file.delete();
        file = new File(Environment.getExternalStorageDirectory(), "signature.png");

        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        gestureView.setDrawingCacheEnabled(true);

        gestureView.setAlwaysDrawnWithCacheEnabled(true);
        gestureView.setHapticFeedbackEnabled(false);
        gestureView.cancelLongPress();
        gestureView.cancelClearAnimation();
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

            @Override
            public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureCancelled(GestureOverlayView arg0,
                                           MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGestureStarted(GestureOverlayView arg0,
                                         MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (arg1.getAction()==MotionEvent.ACTION_MOVE){
                    gestureTouch=false;
                }
                else
                {
                    gestureTouch=true;
                }
            }});

        donebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    bitmap = Bitmap.createBitmap(gestureView.getDrawingCache());
                    if (Build.VERSION.SDK_INT >= 23) {
                        if(checkPermission()){
                            if(!file.exists()){
                                file.createNewFile();
                            }
                            FileOutputStream fos =null;
                            fos = new FileOutputStream(file);
                            // compress to specified format (PNG), quality - which is
                            // ignored for PNG, and out stream
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.close();
                        }else {
                            requestPermission();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(gestureTouch==false)
                { setResult(0);
                    }
                else {
                    setResult(1);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                gestureView.invalidate();
                gestureView.clear(true);
                gestureView.clearAnimation();
                gestureView.cancelClearAnimation();
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DigitalSignatureActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DigitalSignatureActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DigitalSignatureActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DigitalSignatureActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}