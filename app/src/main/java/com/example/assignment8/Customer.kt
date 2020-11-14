package com.example.assignment8

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer(
    @Expose
    @SerializedName("emp_name") val name: String,
    @Expose
    @SerializedName("emp_gender") val gender: String,
    @Expose
    @SerializedName("emp_email") val email: String,
    @Expose
    @SerializedName("emp_salary") val salary: String
) {}