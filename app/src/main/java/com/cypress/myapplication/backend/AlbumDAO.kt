package com.cypress.myapplication.backend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cypress.myapplication.constants.ALBUM_DB
import com.cypress.myapplication.constants.USER_DB
import retrofit2.http.DELETE

@Dao
interface AlbumDAO  {

    @Query("SELECT * FROM $ALBUM_DB")
    fun getAlbums(): LiveData<List<AlbumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(list: List<AlbumEntity>)

    @Query("DELETE FROM $ALBUM_DB")
    suspend fun deleteAlbums()

}