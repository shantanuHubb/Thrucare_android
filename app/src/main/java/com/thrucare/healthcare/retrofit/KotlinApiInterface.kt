package com.thrucare.healthcare.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface KotlinApiInterface {

    @GET
    fun getData(@Url url : String ) : Call<ResponseBody>
}