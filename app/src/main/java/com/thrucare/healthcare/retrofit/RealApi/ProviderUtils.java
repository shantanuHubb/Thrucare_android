package com.thrucare.healthcare.retrofit.RealApi;

import com.thrucare.healthcare.retrofit.ApiInterface;

public class ProviderUtils {

    private static final String BASE_URL_API= "https://a20rhqyk4c.execute-api.us-east-1.amazonaws.com/develop/providers/v1/";

    public static ApiInterface getService()
    {
        return ApiClient.getClient(BASE_URL_API).create(ApiInterface.class);
    }

}
