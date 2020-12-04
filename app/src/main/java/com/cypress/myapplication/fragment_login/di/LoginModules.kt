package com.cypress.myapplication.fragment_login.di

import com.cypress.myapplication.fragment_login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
}

