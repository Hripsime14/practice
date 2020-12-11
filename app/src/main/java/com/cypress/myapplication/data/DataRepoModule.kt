package com.cypress.myapplication.data

import com.cypress.myapplication.fragments.photos.PhotoDataRepo
import com.cypress.myapplication.fragments.users.UsersDataRepo
import com.cypress.myapplication.fragments.users.albums.AlbumsDataRepo
import org.koin.dsl.module

val usersRepoModule = module {
    single {
        UsersDataRepo(get(), get())
    }
}

val albumsRepoModul = module {
    single {
        AlbumsDataRepo(get(), get())
    }
}

val photosRepoModule = module {
    single {
        PhotoDataRepo(get(), get())
    }
}
