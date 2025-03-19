package com.example.jetpackapp.common

import com.example.jetpackapp.data.remotedata.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            handleResponse(apiCall())
        } catch (e: IOException) {
            Resource.Error("Network error, please check your connection", null, e.message!!)
        } catch (e: HttpException) {
            Resource.Error("Server error occurred", e.code(), e.message!!)
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred", null, e.localizedMessage)
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                Resource.Success(response.code(), response.message(), result)
            } ?: Resource.Error("Unknown error", response.code(), response.message())
        } else {
            val error = response.errorBody()?.string() ?: "Unknown error"
            Resource.Error(error, response.code(), response.message())
        }
    }
}
