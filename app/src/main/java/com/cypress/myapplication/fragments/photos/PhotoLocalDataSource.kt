package com.cypress.myapplication.fragments.photos

import com.cypress.myapplication.backend.PhotoDAO
import com.cypress.myapplication.backend.PhotoEntity

class PhotoLocalDataSource(private val photoDao: PhotoDAO) {

    fun getPhotos() = photoDao.getPhotos()

    suspend fun setPhotos(photos: List<PhotoEntity>) = photoDao.insertPhoto(photos)

    suspend fun deletePhotos() = photoDao.deletePhotos()
}