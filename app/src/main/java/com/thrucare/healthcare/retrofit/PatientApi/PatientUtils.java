package com.thrucare.healthcare.retrofit.PatientApi;

import com.thrucare.healthcare.retrofit.ApiInterface;

public class PatientUtils {

    private static final String BASE_URL_API= "https://a20rhqyk4c.execute-api.us-east-1.amazonaws.com/develop/patients/v1/";

    public static ApiInterface getPatientService()
    {
        return PatientApiClient.getClient(BASE_URL_API).create(ApiInterface.class);
    }

}
