package com.cypress.myapplication.backend

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, AlbumEntity::class, PhotoEntity::class], version = 5, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDAO
    abstract fun albumDao(): AlbumDAO
    abstract fun photoDao(): PhotoDAO

}