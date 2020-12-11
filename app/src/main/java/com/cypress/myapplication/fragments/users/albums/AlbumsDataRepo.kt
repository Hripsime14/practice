package com.cypress.myapplication.fragments.users.albums

import android.util.Log
import androidx.lifecycle.LiveData
import com.cypress.myapplication.backend.AlbumEntity
import com.cypress.myapplication.backend.UserEntity
import com.cypress.myapplication.modeldatas.model.AlbumItem
import com.cypress.myapplication.modeldatas.model.UserItem

class AlbumsDataRepo(private val remoteDataSource: AlbumsRemoteDataSource, private val localDataSource: AlbumsLocalDataSource) {

    fun getLocalAlbums(): LiveData<List<AlbumEntity>> = localDataSource.getAlbums()

    suspend fun getRemoteAlbums(id: Int) = remoteDataSource.getAlbums(id)

    suspend fun setLocalAlbums(albums: List<AlbumEntity>) = localDataSource.setAlbums(albums)

    suspend fun deleteAlbums() = localDataSource.deleteAlbums()

}