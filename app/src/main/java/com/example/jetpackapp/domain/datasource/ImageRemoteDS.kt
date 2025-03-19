package com.example.jetpackapp.domain.datasource

import com.example.jetpackapp.data.responsemodel.ImageResponse
import retrofit2.Response

interface ImageRemoteDS {
    suspend fun getImagesData(page: Int): Response<List<ImageResponse>>
}