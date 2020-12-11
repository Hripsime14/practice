package com.cypress.myapplication.fragments.photos

import com.cypress.myapplication.backend.PhotoEntity

class PhotoDataRepo(private val remoteDataSource: PhotoRemoteDataSource, private val localDataSource: PhotoLocalDataSource) {

    suspend fun getRemotePhotos(albumId: Int) = remoteDataSource.getPhotos(albumId)

    fun getLocalPhotos() = localDataSource.getPhotos()

    suspend fun setLocalPhotos(photos: List<PhotoEntity>) = localDataSource.setPhotos(photos)

    suspend fun deletePhotos() = localDataSource.deletePhotos()


}