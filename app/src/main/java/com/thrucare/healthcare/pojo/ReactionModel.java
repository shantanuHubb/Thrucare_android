package com.thrucare.healthcare.pojo;

public class ReactionModel {
    int isAdd ;
    String reactionValue ;
    String reactionValueForAPI ;
    public String getReactionValueForAPI() {
        return reactionValueForAPI;
    }

    public void setReactionValueForAPI(String reactionValueForAPI) {
        this.reactionValueForAPI = reactionValueForAPI;
    }



    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }

    public String getReactionValue() {
        return reactionValue;
    }

    public void setReactionValue(String reactionValue) {
        this.reactionValue = reactionValue;
    }

    public ReactionModel(int isAdd, String reactionValue , String reactionValueForAPI) {
        this.isAdd = isAdd;
        this.reactionValue = reactionValue;
        this.reactionValueForAPI = reactionValueForAPI;
    }
}
