package com.cypress.myapplication.fragments.users.albums.di

import com.cypress.myapplication.fragments.users.albums.AlbumsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val albumsViewModel = module {
    viewModel { (userId: Int) -> AlbumsViewModel(userId, get(), get())
    }
}

