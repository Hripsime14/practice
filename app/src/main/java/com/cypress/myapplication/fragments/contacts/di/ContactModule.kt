package com.cypress.myapplication.fragments.contacts.di

import com.cypress.myapplication.fragments.contacts.ContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contactViewModelModule = module {
    viewModel {
        ContactViewModel(get())
    }
}