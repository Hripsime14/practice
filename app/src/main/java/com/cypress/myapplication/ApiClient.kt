package com.cypress.myapplication

import com.cypress.myapplication.modeldatas.model.AlbumItem
import com.cypress.myapplication.modeldatas.model.PhotoItem
import com.cypress.myapplication.modeldatas.model.UserItem
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("users")
    suspend fun getUsers(): List<UserItem>

    @GET("albums")
    suspend fun getAlbums(@Query("userId") userId: Int): List<AlbumItem>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<PhotoItem>
}