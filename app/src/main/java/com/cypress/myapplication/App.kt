package com.cypress.myapplication

import android.app.Application
import com.cypress.myapplication.constants.baseUrl
import com.cypress.myapplication.data.*
import com.cypress.myapplication.fragments.contacts.di.contactViewModelModule
import com.cypress.myapplication.fragments.intro.di.introViewModelModule
import com.cypress.myapplication.fragments.login.di.loginViewModelModule
import com.cypress.myapplication.fragments.media.di.mediaViewModelModule
import com.cypress.myapplication.fragments.users.albums.di.albumsViewModel
import com.cypress.myapplication.fragments.users.di.usersViewModelModule
import com.cypress.myapplication.main.di.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                createRemoteModule(baseUrl),
                localDataModule,
                usersRepoModule,
                mainViewModelModule,
                usersViewModelModule,
                introViewModelModule,
                loginViewModelModule,
                albumsRepoModule,
                albumsViewModel,
                photosRepoModule,
                contactViewModelModule,
                contactRepoModule,
                mediaViewModelModule,
                mediaRepoModule
            ))
        }
    }
}