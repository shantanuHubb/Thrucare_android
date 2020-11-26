package com.thrucare.healthcare.activity.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.databinding.ActivityEvidenceMedicalBinding;

import java.util.ArrayList;
import java.util.List;

public class EvidenceMedicalActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityEvidenceMedicalBinding binding;
    private OnClickOnContinue onClickOnContinue ;

    private int okayTickOne = 0;
    private int okayTickTwo = 0;
    private int okayTickThree = 0;
    private int okayTickFour  = 0;
    private String fileNameOne  = "";
    private String fileNameTwo  = "";
    private String fileNameThree  = "";
    private String fileNameFour  = "";

    List<String> listReport  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEvidenceMedicalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(view);

        onClickOnContinue  = (OnClickOnContinue) AddDiagnosisActivity.activity;
        setListeners();

}

    private void setListeners() {
        binding.cardOneMr.setOnClickListener(this);
        binding.cardTwoMr.setOnClickListener(this);
        binding.cardThreeMr.setOnClickListener(this);
        binding.cardFourMr.setOnClickListener(this);
        binding.btnContinueMedicalReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.card_one_mr:
                if(okayTickOne==0){
                    binding.imgOneOkayTick.setVisibility(View.VISIBLE);
                    okayTickOne = 1;
                    fileNameOne  = "report1";
                }else {
                    binding.imgOneOkayTick.setVisibility(View.GONE);
                    okayTickOne = 0;
                    fileNameOne  = "";
                }

                break;

            case R.id.card_two_mr:
                if(okayTickTwo==0){
                    binding.imgTwoOkayTick.setVisibility(View.VISIBLE);
                    okayTickTwo = 1;
                    fileNameTwo  = "report2";
                }else {
                    binding.imgTwoOkayTick.setVisibility(View.GONE);
                    okayTickTwo = 0;
                    fileNameTwo  = "";
                }
                break;

            case R.id.card_three_mr:
                if(okayTickThree==0){
                    binding.imgThreeOkayTick.setVisibility(View.VISIBLE);
                    okayTickThree = 1;
                    fileNameThree  = "report3";
                }else {
                    binding.imgThreeOkayTick.setVisibility(View.GONE);
                    okayTickThree = 0;
                    fileNameThree  = "";
                }
                break;

            case R.id.card_four_mr:
                if(okayTickFour==0){
                    binding.imgFourOkayTick.setVisibility(View.VISIBLE);
                    okayTickFour = 1;
                    fileNameFour  = "report4";
                }else {
                    binding.imgFourOkayTick.setVisibility(View.GONE);
                    okayTickFour = 0;
                    fileNameFour  = "";
                }
                break;


            case R.id.btn_continue_medical_report:
              /*  PreferenceManager.getDefaultSharedPreferences(this).edit().putString("FILE_NAME",
                        fileNameOne+fileNameTwo+fileNameThree+fileNameFour).apply();*/
                StringBuffer  sb  = new StringBuffer();
                if(!fileNameOne.isEmpty()){
                    listReport.add(fileNameOne);
                }
                if(!fileNameTwo.isEmpty()){
                    listReport.add(fileNameTwo);
                }
                if(!fileNameThree.isEmpty()){
                    listReport.add(fileNameThree);                }
                if(!fileNameFour.isEmpty()){
                    listReport.add(fileNameFour);
                }
              for(int i =0 ; i<listReport.size(); i++) {
                  if(!listReport.get(i).isEmpty()){
                      sb.append(listReport.get(i));
                      if(listReport.size()-1!=i){
                          sb.append(",");
                      }
                      ;
                  }
              }

                onClickOnContinue.getSelectedMedicalReport(sb);
               // onClickOnContinue.getSelectedMedicalReport(fileNameOne+","+fileNameTwo+","+fileNameThree+","+fileNameFour);
                super.onBackPressed();
                break;
        }
    }



    interface OnClickOnContinue{
       void  getSelectedMedicalReport(StringBuffer value);
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