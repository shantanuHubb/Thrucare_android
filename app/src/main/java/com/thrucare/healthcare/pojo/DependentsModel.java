package com.thrucare.healthcare.pojo;

public class DependentsModel {
    public String getDependentsName() {
        return dependentsName;
    }

    public void setDependentsName(String dependentsName) {
        this.dependentsName = dependentsName;
    }

    public String getDependentEmail() {
        return dependentEmail;
    }

    public void setDependentEmail(String dependentEmail) {
        this.dependentEmail = dependentEmail;
    }

    String dependentsName ;
    String dependentEmail;

    public DependentsModel(String dependentsName, String dependentEmail) {
        this.dependentsName = dependentsName;
        this.dependentEmail = dependentEmail;
    }
}
