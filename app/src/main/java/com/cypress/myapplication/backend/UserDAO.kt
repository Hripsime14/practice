package com.cypress.myapplication.backend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cypress.myapplication.constants.USER_DB


@Dao
interface UserDAO {

    @Query("SELECT * FROM $USER_DB")
    fun getUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserEntity>)

    @Query("DELETE FROM $USER_DB")
    suspend fun deleteUsers()

}