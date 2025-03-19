package com.example.jetpackapp.di

import com.example.jetpackapp.data.implementations.datasourceimpl.ImageRemoteDataSourceImpl
import com.example.jetpackapp.data.remotedata.ApiService
import com.example.jetpackapp.domain.datasource.ImageRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideImageRemoteDataSource(apiService: ApiService): ImageRemoteDS {
        return ImageRemoteDataSourceImpl(apiService)
    }
}