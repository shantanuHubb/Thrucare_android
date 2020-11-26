package com.thrucare.healthcare.pojo;

public class QuestionInternalModel {

    private String code;
    private String display;
    private String type;
    private String answer;


    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    private String questionNumber;

    public QuestionInternalModel(String code, String display, String type, String answer , String questionNumber) {
        this.code = code;
        this.display = display;
        this.type = type;
        this.answer = answer;
        this.questionNumber = questionNumber;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }



    public void setDisplay(String display) {
        this.display = display;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
