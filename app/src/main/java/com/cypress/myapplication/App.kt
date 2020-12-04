package com.cypress.myapplication

import android.app.Application
import com.cypress.myapplication.data.localDataModule
import com.cypress.myapplication.fragment_intro.di.introViewModelModule
import com.cypress.myapplication.fragment_login.di.loginViewModelModule
import com.cypress.myapplication.main.di.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                localDataModule,
                mainViewModelModule,
                introViewModelModule,
                loginViewModelModule))
        }
    }
}