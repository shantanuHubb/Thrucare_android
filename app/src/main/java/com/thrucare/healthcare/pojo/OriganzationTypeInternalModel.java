package com.thrucare.healthcare.pojo;

public class OriganzationTypeInternalModel {
    public String code;
    public String value;
    public int isSelected;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }



    public OriganzationTypeInternalModel(String code, String value, int isSelected) {
        this.code = code;
        this.value = value;
        this.isSelected = isSelected;
    }
}
