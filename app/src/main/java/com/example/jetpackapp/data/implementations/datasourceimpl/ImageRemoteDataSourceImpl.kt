package com.example.jetpackapp.data.implementations.datasourceimpl

import com.example.jetpackapp.data.remotedata.ApiService
import com.example.jetpackapp.data.responsemodel.ImageResponse
import com.example.jetpackapp.domain.datasource.ImageRemoteDS
import retrofit2.Response

class ImageRemoteDataSourceImpl(private val apiService: ApiService): ImageRemoteDS {
    override suspend fun getImagesData(page: Int): Response<List<ImageResponse>> {
        return apiService.getImages(page,30)
    }
}