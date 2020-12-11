package com.cypress.myapplication.fragments.users.albums


import android.util.Log
import androidx.lifecycle.LiveData
import com.cypress.myapplication.backend.AlbumDAO
import com.cypress.myapplication.backend.AlbumEntity

class AlbumsLocalDataSource(private val albumDao: AlbumDAO) {

    fun getAlbums(): LiveData<List<AlbumEntity>>  = albumDao.getAlbums()

    suspend fun setAlbums(albums: List<AlbumEntity>) {
        Log.d("seqs", "setAlbums: ")
        return albumDao.insertAlbums(albums)
    }

    suspend fun deleteAlbums() = albumDao.deleteAlbums()
}