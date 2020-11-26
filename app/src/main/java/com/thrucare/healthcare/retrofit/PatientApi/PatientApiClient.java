package com.thrucare.healthcare.retrofit.PatientApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient(String BaseUrl)
    {
     if(retrofit == null)
     {
         retrofit = new Retrofit.Builder()
                 .baseUrl(BaseUrl)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
     }
     return retrofit;
    }
}
