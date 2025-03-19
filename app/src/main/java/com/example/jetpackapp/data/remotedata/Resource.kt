package com.example.jetpackapp.data.remotedata

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null,
    val errorBody: String? = null
) {
    class Success<T>(statusCode: Int?, message: String, data: T) : Resource<T>(
        statusCode = statusCode, message = message,
        data = data
    )

    class Error<T>(errorMessage: String, statusCode: Int?, message: String, data: T? = null) : Resource<T>(
            statusCode = statusCode, message = message,
            data = data, errorBody = errorMessage)

    class Loading<T>(data: T? = null) : Resource<T>(data)

}