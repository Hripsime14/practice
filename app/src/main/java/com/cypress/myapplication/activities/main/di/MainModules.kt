package com.cypress.myapplication.activities.main.di

import com.cypress.myapplication.activities.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}