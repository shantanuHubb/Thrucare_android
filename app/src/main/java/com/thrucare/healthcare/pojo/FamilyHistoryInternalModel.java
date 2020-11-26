package com.thrucare.healthcare.pojo;

public class FamilyHistoryInternalModel {
    public String getTvRelationName() {
        return tvRelationName;
    }

    public void setTvRelationName(String tvRelationName) {
        this.tvRelationName = tvRelationName;
    }

    public String getTvProblemOne() {
        return tvProblemOne;
    }

    public void setTvProblemOne(String tvProblemOne) {
        this.tvProblemOne = tvProblemOne;
    }

    public String getTvProblemTwo() {
        return tvProblemTwo;
    }

    public void setTvProblemTwo(String tvProblemTwo) {
        this.tvProblemTwo = tvProblemTwo;
    }

    String tvRelationName ;
    String tvProblemOne ;
    String tvProblemTwo ;

    public FamilyHistoryInternalModel(String tvRelationName, String tvProblemOne, String tvProblemTwo) {
        this.tvRelationName = tvRelationName;
        this.tvProblemOne = tvProblemOne;
        this.tvProblemTwo = tvProblemTwo;
    }
}
