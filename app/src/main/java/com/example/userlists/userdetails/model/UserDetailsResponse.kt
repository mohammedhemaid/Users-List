package com.example.userlists.userdetails.model

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("data")
    val userDetails: UserDetails,
    @SerializedName("status")
    val status: String
)