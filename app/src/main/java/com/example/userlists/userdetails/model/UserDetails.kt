package com.example.userlists.userdetails.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetails(
    @SerializedName("age")
    val age: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lastName")
    val lastName: String
) : Parcelable