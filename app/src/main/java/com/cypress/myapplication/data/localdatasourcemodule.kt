package com.cypress.myapplication.data

import android.content.Context
import com.cypress.myapplication.fragment_intro.IntroDataRepo
import com.cypress.myapplication.fragment_intro.IntroLocalDataSource
import com.cypress.myapplication.fragment_login.LoginDataRepo
import com.cypress.myapplication.fragment_login.LoginLocalDataSource
import com.cypress.myapplication.main.MainDataRepo
import com.cypress.myapplication.main.MainLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
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





}