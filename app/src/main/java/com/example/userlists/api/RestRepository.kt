package com.example.userlists.api

import com.example.userlists.api.errorresponse.Error
import com.example.userlists.api.errorresponse.ErrorResponse
import com.example.userlists.api.servicegenerator.UsersService
import com.example.userlists.usersList.model.UserListsResponse
import com.example.userlists.userdetails.model.UserDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class RestRepository(private val usersService: UsersService) {

    suspend fun getUsersId(): Resource<UserListsResponse, ErrorResponse> {
        return when (val response = processCall(
            usersService.getUsers(
                ApiConstants.TOKEN
            )
        )) {
            is UserListsResponse -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorData = response as ErrorResponse)
            }
        }
    }

    suspend fun getUserDetails(postId: String): Resource<UserDetailsResponse, ErrorResponse> {
        return when (val response = processCall(
            usersService.getUsersDetails(
                ApiConstants.TOKEN,
                postId
            )
        )) {
            is UserDetailsResponse -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorData = response as ErrorResponse)
            }
        }
    }

    private suspend fun processCall(response: Response<*>): Any? {
        return withContext(Dispatchers.IO) {
            try {
                if (response.isSuccessful) {
                    response.body()
                } else {
                    try {
                        val errorResponse = JSONObject(response.errorBody()?.string() ?: "")
                        if (errorResponse.has("Error")
                            && errorResponse.getJSONObject("error").has("message")
                        ) {
                            ErrorResponse(
                                Error(
                                    errorResponse.getJSONObject("error")
                                        .getString("message")
                                )
                            )
                        } else {
                            ErrorResponse()
                        }
                    } catch (e: Exception) {
                        ErrorResponse()
                    }
                }
            } catch (e: IOException) {
                ErrorResponse()
            }
        }
    }
}