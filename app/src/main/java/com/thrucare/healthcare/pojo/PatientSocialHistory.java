package com.thrucare.healthcare.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientSocialHistory {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("tobacco")
    @Expose
    private String tobacco;
    @SerializedName("alcohol")
    @Expose
    private String alcohol;
    @SerializedName("recreational_drugs")
    @Expose
    private String recreationalDrugs;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getRecreationalDrugs() {
        return recreationalDrugs;
    }

    public void setRecreationalDrugs(String recreationalDrugs) {
        this.recreationalDrugs = recreationalDrugs;
    }
}
