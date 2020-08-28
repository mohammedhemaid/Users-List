package com.example.userlists.api.errorresponse


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message")
    val message: String
)