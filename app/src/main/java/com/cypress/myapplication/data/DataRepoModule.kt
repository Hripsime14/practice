package com.cypress.myapplication.data

import com.cypress.myapplication.fragments.contacts.ContactDataRepo
import com.cypress.myapplication.fragments.intro.IntroDataRepo
import com.cypress.myapplication.fragments.login.LoginDataRepo
import com.cypress.myapplication.fragments.media.MediaDataRepo
import com.cypress.myapplication.fragments.photos.PhotoDataRepo
import com.cypress.myapplication.fragments.users.UsersDataRepo
import com.cypress.myapplication.fragments.users.albums.AlbumsDataRepo
import com.cypress.myapplication.activities.main.MainDataRepo
import org.koin.dsl.module

val introRepoModule = module {
    single {
        IntroDataRepo(get())
    }
}

val loginRepoModule = module {
    single {
        LoginDataRepo(get())
    }
}

val mainRepoModule = module {
    single {
        MainDataRepo(get())
    }
}

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
