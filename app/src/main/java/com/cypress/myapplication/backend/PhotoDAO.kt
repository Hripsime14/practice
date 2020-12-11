package com.cypress.myapplication.backend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cypress.myapplication.constants.PHOTO_DB
import com.cypress.myapplication.constants.USER_DB

@Dao
interface PhotoDAO {

    @Query("SELECT * FROM $PHOTO_DB")
    fun getPhotos(): LiveData<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(users: List<PhotoEntity>)

    @Query("DELETE FROM $PHOTO_DB")
    suspend fun deletePhotos()

}