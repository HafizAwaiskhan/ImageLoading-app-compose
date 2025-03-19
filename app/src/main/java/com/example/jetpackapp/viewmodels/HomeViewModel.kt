package com.example.jetpackapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackapp.MyApplication
import com.example.jetpackapp.R
import com.example.jetpackapp.data.remotedata.Resource
import com.example.jetpackapp.data.responsemodel.ImageResponse
import com.example.jetpackapp.domain.repository.ImageRepository
import com.example.jetpackapp.utils.INTERNET_UNAVAILABLE_CODE
import com.example.jetpackapp.utils.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _getImagesFromApiResponse =
        MutableStateFlow<Resource<List<ImageResponse>>>(Resource.Loading())
    val getImagesFromApiResponse: StateFlow<Resource<List<ImageResponse>>> get() = _getImagesFromApiResponse

    private val _allImages = MutableStateFlow<List<ImageResponse>>(emptyList())
    val allImages: StateFlow<List<ImageResponse>> get() = _allImages.asStateFlow()

    private var currentPage = 1
    private var isLoading = false

    init {
        getImages(currentPage)  // Load first page initially
    }

    private fun getImages(page: Int) = viewModelScope.launch(dispatcher) {
        if (isLoading) return@launch  // Prevent duplicate API calls
        isLoading = true

        if (page == 1) {
            _getImagesFromApiResponse.value = Resource.Loading()
        }

        try {
            if (NetworkUtil.hasInternetConnection(MyApplication.appContext)) {
                Log.d("*****", "page For Api : $currentPage")
                val apiResponse = repository.getImages(page)
                if (apiResponse is Resource.Success) {
                    val newImages = apiResponse.data ?: emptyList()
                    _allImages.value = _allImages.value + newImages // Append new images
                }
                _getImagesFromApiResponse.value = apiResponse
            } else {
                _getImagesFromApiResponse.value = Resource.Error(
                    MyApplication.appContext.getString(R.string.internet_is_unavailable),
                    INTERNET_UNAVAILABLE_CODE,
                    MyApplication.appContext.getString(R.string.internet_is_unavailable)
                )
            }
        } catch (e: Exception) {
            _getImagesFromApiResponse.value = Resource.Error(
                e.toString(), e.cause?.hashCode() ?: 0, e.message.toString()
            )
        }
        isLoading = false
    }

    fun saveImage(image: ImageResponse, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.saveToFavorites(image)
            onResult(success)
        }
    }

    val favoriteImages: StateFlow<List<ImageResponse>> =
        repository.getFavoriteImages().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeImage(image: ImageResponse) = viewModelScope.launch {
        repository.removeFromFavorites(image)
    }

    fun loadNextPage() {
        if (!isLoading) {
            currentPage++
            Log.d("*****", "pageSize: $currentPage")
            getImages(currentPage)
        }
    }

}