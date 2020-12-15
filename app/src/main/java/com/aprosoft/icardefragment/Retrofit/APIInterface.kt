package com.aprosoft.icardefragment.Retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIInterface {


    @FormUrlEncoded
    @POST("InsertDetails")
    fun signUp(@FieldMap params:HashMap<String, String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("UserLogin")
    fun login(@FieldMap params: HashMap<String, String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("createMyCard")
    fun createCard(@FieldMap params: HashMap<String, String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("viewMyCard")
    fun myCards(@FieldMap params: HashMap<String, String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("DisplayData")
    fun profile(@FieldMap params: HashMap<String, String>):Call<ResponseBody>



}