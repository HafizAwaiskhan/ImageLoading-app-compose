package com.example.jetpackapp.domain.repository

import com.example.jetpackapp.data.remotedata.Resource
import com.example.jetpackapp.data.responsemodel.ImageResponse
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getImages(page: Int): Resource<List<ImageResponse>>
    suspend fun saveToFavorites(image: ImageResponse): Boolean
    suspend fun removeFromFavorites(image: ImageResponse)
    fun getFavoriteImages(): Flow<List<ImageResponse>>
}

