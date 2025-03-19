package com.example.jetpackapp.data.remotedata

import com.example.jetpackapp.data.responsemodel.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") perPage: Int
    ): Response<List<ImageResponse>>
}