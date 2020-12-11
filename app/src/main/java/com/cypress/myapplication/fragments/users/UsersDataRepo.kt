package com.cypress.myapplication.fragments.users

import android.util.Log
import androidx.lifecycle.LiveData
import com.cypress.myapplication.backend.UserEntity

class UsersDataRepo(private val remoteDataSource: UsersRemoteDataSource, private val localDataSource: UsersLocalDataSource) {

    suspend fun getUsers() = remoteDataSource.getUsers()

    fun getUsersDB(): LiveData<List<UserEntity>> {
        return localDataSource.getUsersDB()
    }

    suspend fun setUsersDB(users: List<UserEntity>) {
        return localDataSource.setUsersDB(users)
    }
}