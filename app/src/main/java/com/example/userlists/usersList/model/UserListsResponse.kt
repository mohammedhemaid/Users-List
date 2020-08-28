package com.example.userlists.usersList.model


import com.google.gson.annotations.SerializedName

data class UserListsResponse(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("status")
    val status: String
)