package com.example.assignment8

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface customerAPI {
    @GET("allcustomer")
    fun retrieveCustomer(): Call<List<Customer>>

    @FormUrlEncoded
    @POST("customer")
    fun insertCustomer(
            @Field("emp_name") name: String,
            @Field("emp_gender") gender: String,
            @Field("emp_email") email: String,
            @Field("emp_salary") salary: String
    ): Call<Customer>
}