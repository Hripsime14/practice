package com.cypress.myapplication.fragments.users

import com.cypress.myapplication.ApiClient

class UsersRemoteDataSource(private val apiClient: ApiClient) {

    suspend fun getUsers() = apiClient.getUsers()
}