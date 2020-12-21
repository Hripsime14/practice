package com.cypress.myapplication.fragments.media.di

import com.cypress.myapplication.fragments.media.MediaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaViewModelModule = module {
    viewModel {
        MediaViewModel(get())
    }
}