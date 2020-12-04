package com.cypress.myapplication.fragment_intro.di

import com.cypress.myapplication.fragment_intro.IntroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val introViewModelModule = module {
    viewModel {
        IntroViewModel(get())
    }
}

