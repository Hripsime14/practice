package com.cypress.myapplication.fragments.photos

import com.cypress.myapplication.ApiClient

class PhotoRemoteDataSource(private val apiClient: ApiClient) {

    suspend fun getPhotos(albumId: Int) = apiClient.getPhotos(albumId)

}