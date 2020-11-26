package com.thrucare.healthcare.pojo;

public class TimeSlotModel {
    public TimeSlotModel(String value, int isSelection , String status  , boolean isOverBook) {
        this.value = value;
        this.isSelection = isSelection;
        this.status  = status;
        this.isOverBook  = isOverBook;


    }

    public String value ;
    public int isSelection;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOverBook() {
        return isOverBook;
    }

    public void setOverBook(boolean overBook) {
        isOverBook = overBook;
    }

    public String  status;
    public boolean isOverBook;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIsSelection() {
        return isSelection;
    }

    public void setIsSelection(int isSelection) {
        this.isSelection = isSelection;
    }
}
