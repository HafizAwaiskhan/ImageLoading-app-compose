package com.example.joinappstudioassignment.data.localdata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.jetpackapp.data.responsemodel.ImageResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: ImageResponse)

    @Query("SELECT COUNT(*) FROM ImageResponse WHERE id = :id")
    suspend fun isImageExists(id: String): Boolean

    @Transaction
    suspend fun insertIfNotExists(image: ImageResponse) {
        if (!isImageExists(image.id)) {
            insert(image)
        }
    }

    @Delete
    suspend fun delete(image: ImageResponse)

    @Query("SELECT * FROM ImageResponse")
    fun getFavorites(): Flow<List<ImageResponse>>
}
