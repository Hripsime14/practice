package com.cypress.myapplication.fragments.users

import androidx.lifecycle.LiveData
import com.cypress.myapplication.backend.UserDAO
import com.cypress.myapplication.backend.UserEntity

class UsersLocalDataSource(private val userDao: UserDAO) {

    fun getUsersDB(): LiveData<List<UserEntity>> = userDao.getUsers()

    suspend fun setUsersDB(users: List<UserEntity>) = userDao.insertUser(users)

}