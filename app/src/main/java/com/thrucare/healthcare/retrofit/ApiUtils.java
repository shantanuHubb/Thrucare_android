package com.thrucare.healthcare.retrofit;

import com.thrucare.healthcare.retrofit.ApiClient;
import com.thrucare.healthcare.retrofit.ApiInterface;


public class ApiUtils {
        private static final String BASE_URL_API_THRUCARE= "https://private-a4094-thrucare.apiary-mock.com";
    public static ApiInterface getApiService()
    {
        return ApiClient.getClient(BASE_URL_API_THRUCARE).create(ApiInterface.class);
    }
}
