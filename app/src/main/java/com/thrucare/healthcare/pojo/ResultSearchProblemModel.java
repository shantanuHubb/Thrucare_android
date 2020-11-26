package com.thrucare.healthcare.pojo;

public class ResultSearchProblemModel {
    String code;

    public String getCode() {
        return code;
    }

    public ResultSearchProblemModel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

}
