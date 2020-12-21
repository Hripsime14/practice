package com.cypress.myapplication.data

import android.content.Context
import androidx.room.Room
import com.cypress.myapplication.backend.UserDatabase
import com.cypress.myapplication.constants.USER_DB
import com.cypress.myapplication.fragments.contacts.ContactLocalDataSource
import com.cypress.myapplication.fragments.intro.IntroDataRepo
import com.cypress.myapplication.fragments.intro.IntroLocalDataSource
import com.cypress.myapplication.fragments.login.LoginDataRepo
import com.cypress.myapplication.fragments.login.LoginLocalDataSource
import com.cypress.myapplication.fragments.media.MediaLocalDataSource
import com.cypress.myapplication.fragments.photos.PhotoLocalDataSource
import com.cypress.myapplication.fragments.photos.PhotoRemoteDataSource
import com.cypress.myapplication.fragments.users.UsersLocalDataSource
import com.cypress.myapplication.fragments.users.albums.AlbumsLocalDataSource
import com.cypress.myapplication.main.MainDataRepo
import com.cypress.myapplication.main.MainLocalDataSource
import com.cypress.myapplication.manager.ContactManager
import com.cypress.myapplication.manager.MediaManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single {
        Room.databaseBuilder(get(), UserDatabase::class.java, USER_DB).fallbackToDestructiveMigration().build()
    }

    fun provideUserDao(db: UserDatabase) = db.userDao()
    single {
        provideUserDao(get())
    }

    fun provideAlbumDao(db: UserDatabase) = db.albumDao()
    single {
        provideAlbumDao(get())
    }

    fun providePhotoDao(db: UserDatabase) = db.photoDao()
    single {
        providePhotoDao(get())
    }

    single {
        androidContext().getSharedPreferences("PREF", Context.MODE_PRIVATE)
    }

    single {
        IntroLocalDataSource(get())
    }

    single {
        IntroDataRepo(get())
    }

    single {
        LoginLocalDataSource(get())
    }

    single {
        LoginDataRepo(get())
    }

    single {
        MainLocalDataSource(get())
    }

    single {
        MainDataRepo(get())
    }

    single {
        UsersLocalDataSource(get())
    }

    single {
        AlbumsLocalDataSource(get())
    }

    single {
        PhotoLocalDataSource(get())
    }

    single {
        PhotoRemoteDataSource(get())
    }

    single {
        ContactManager()
    }

    single {
        ContactLocalDataSource(get())
    }

    single {
        MediaManager()
    }

    single {
        MediaLocalDataSource(get())
    }
}