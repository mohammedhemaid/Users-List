package com.example.userlists.api.servicegenerator

import com.example.userlists.api.ApiConstants
import com.example.userlists.usersList.model.UserListsResponse
import com.example.userlists.userdetails.model.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsersService {

    @GET("list")
    suspend fun getUsers(
            @Header(ApiConstants.TOKEN_HEADER) token: String
    ): Response<UserListsResponse>

    @GET("get/{id}")
    suspend fun getUsersDetails(
        @Header(ApiConstants.TOKEN_HEADER) token: String,
        @Path("id") userId: String
    ): Response<UserDetailsResponse>
}