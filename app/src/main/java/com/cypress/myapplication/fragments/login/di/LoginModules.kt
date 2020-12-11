package com.cypress.myapplication.fragments.login.di

import com.cypress.myapplication.fragments.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
}

