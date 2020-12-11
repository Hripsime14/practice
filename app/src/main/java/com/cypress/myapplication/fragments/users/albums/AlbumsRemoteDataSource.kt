package com.cypress.myapplication.fragments.users.albums

import com.cypress.myapplication.ApiClient

class AlbumsRemoteDataSource(private val apiClient: ApiClient) {

    suspend fun getAlbums(userId: Int) = apiClient.getAlbums(userId)

}