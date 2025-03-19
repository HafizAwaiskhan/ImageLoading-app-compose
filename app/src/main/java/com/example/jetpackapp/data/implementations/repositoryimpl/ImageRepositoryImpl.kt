package com.example.jetpackapp.data.implementations.repositoryimpl

import android.util.Log
import com.example.jetpackapp.common.BaseRepository
import com.example.joinappstudioassignment.data.localdata.dao.ImageDao
import com.example.jetpackapp.data.remotedata.Resource
import com.example.jetpackapp.data.responsemodel.ImageResponse
import com.example.jetpackapp.domain.datasource.ImageRemoteDS
import com.example.jetpackapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImpl(
    private val imagesRemoteDataSource: ImageRemoteDS,
    private val imageDao: ImageDao
) : BaseRepository(), ImageRepository {

    override suspend fun getImages(page: Int): Resource<List<ImageResponse>> {
        Log.d("*****", "Repo Impl: $page")
        return safeApiCall { imagesRemoteDataSource.getImagesData(page) }
    }

    override suspend fun saveToFavorites(image: ImageResponse): Boolean {
        return if (!imageDao.isImageExists(image.id)) {
            imageDao.insertIfNotExists(image)
            true // Image saved successfully
        } else {
            false // Image already exists
        }
    }

    override suspend fun removeFromFavorites(image: ImageResponse) {
        imageDao.delete(image)
    }

    override fun getFavoriteImages(): Flow<List<ImageResponse>> = imageDao.getFavorites()
}