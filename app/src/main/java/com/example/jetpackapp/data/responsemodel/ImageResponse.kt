package com.example.jetpackapp.data.responsemodel

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ImageResponse(
    @PrimaryKey val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)