package com.cypress.myapplication.fragments.intro.di

import com.cypress.myapplication.fragments.intro.IntroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val introViewModelModule = module {
    viewModel {
        IntroViewModel(get())
    }
}

