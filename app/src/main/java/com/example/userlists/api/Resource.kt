package com.example.userlists.api

// A generic class that contains userDetails and status about loading this userDetails.
sealed class Resource<T, E>(
    val data: T? = null,
    val errorData: E? = null
) {
    class Success<T, E>(data: T) : Resource<T, E>(data)
    class DataError<T, E>(errorData: E? = null) : Resource<T, E>(null, errorData)
}