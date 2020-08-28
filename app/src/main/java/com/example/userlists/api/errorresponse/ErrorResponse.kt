package com.example.userlists.api.errorresponse


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: Error = Error("Error getting some users"),
    @SerializedName("status")
    val status: String = "Error"
)