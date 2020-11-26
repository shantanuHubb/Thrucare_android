package com.thrucare.healthcare.pojo;

public class MedicationPojp {

    public String getTvName() {
        return tvName;
    }

    public MedicationPojp(String tvName, String tvReason, String tvDate) {
        this.tvName = tvName;
        this.tvReason = tvReason;
        this.tvDate = tvDate;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvReason() {
        return tvReason;
    }

    public void setTvReason(String tvReason) {
        this.tvReason = tvReason;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    String tvName;
    String tvReason;
    String tvDate ;

}
