package com.cypress.myapplication.data

import com.cypress.myapplication.fragments.contacts.ContactDataRepo
import com.cypress.myapplication.fragments.media.MediaDataRepo
import com.cypress.myapplication.fragments.photos.PhotoDataRepo
import com.cypress.myapplication.fragments.users.UsersDataRepo
import com.cypress.myapplication.fragments.users.albums.AlbumsDataRepo
import org.koin.dsl.module

val usersRepoModule = module {
    single {
        UsersDataRepo(get(), get())
    }
}

val albumsRepoModule = module {
    single {
        AlbumsDataRepo(get(), get())
    }
}

val photosRepoModule = module {
    single {
        PhotoDataRepo(get(), get())
    }
}

val contactRepoModule = module {
    single {
        ContactDataRepo(get())
    }
}

val mediaRepoModule = module {
    single {
        MediaDataRepo(get())
    }
}
