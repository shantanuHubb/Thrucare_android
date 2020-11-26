package com.thrucare.healthcare.pojo;

public class ProductInsuranceModel {
    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }

    public String getSelectedCode() {
        return selectedCode;
    }

    public void setSelectedCode(String selectedCode) {
        this.selectedCode = selectedCode;
    }

    int isAdd ;
    String selectedCode ;
    String selectedDisplay ;

    public String getSelectedDisplay() {
        return selectedDisplay;
    }

    public void setSelectedDisplay(String selectedDisplay) {
        this.selectedDisplay = selectedDisplay;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    int  selectedPosition;

    public ProductInsuranceModel(int isAdd, String selectedCode , int selectedPosition , String selectedDisplay) {
        this.isAdd = isAdd;
        this.selectedCode = selectedCode;
        this.selectedPosition  = selectedPosition;
        this.selectedDisplay = selectedDisplay;
    }
}
