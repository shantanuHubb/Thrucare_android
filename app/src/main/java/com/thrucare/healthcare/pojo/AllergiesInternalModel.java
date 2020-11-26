package com.thrucare.healthcare.pojo;

public class AllergiesInternalModel {
    public String getTvDocName() {
        return tvDocName;
    }

    public AllergiesInternalModel(String tvDocName, String subtanceName, String cricilityName, String onSetDate) {
        this.tvDocName = tvDocName;
        this.subtanceName = subtanceName;
        this.cricilityName = cricilityName;
        this.onSetDate = onSetDate;
    }

    public void setTvDocName(String tvDocName) {
        this.tvDocName = tvDocName;
    }

    public String getSubtanceName() {
        return subtanceName;
    }

    public void setSubtanceName(String subtanceName) {
        this.subtanceName = subtanceName;
    }

    public String getCricilityName() {
        return cricilityName;
    }

    public void setCricilityName(String cricilityName) {
        this.cricilityName = cricilityName;
    }

    public String getOnSetDate() {
        return onSetDate;
    }

    public void setOnSetDate(String onSetDate) {
        this.onSetDate = onSetDate;
    }

    String tvDocName ;
    String subtanceName ;
    String cricilityName;
    String onSetDate ;

}
