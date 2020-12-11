package com.cypress.myapplication.fragments.users.di

import com.cypress.myapplication.fragments.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersViewModelModule = module {
    viewModel {
        UsersViewModel(get())
    }
}