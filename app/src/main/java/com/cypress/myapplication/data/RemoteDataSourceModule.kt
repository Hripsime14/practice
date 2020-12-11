package com.cypress.myapplication.data
import com.cypress.myapplication.ApiClient
import com.cypress.myapplication.fragments.users.UsersRemoteDataSource
import com.cypress.myapplication.fragments.users.albums.AlbumsRemoteDataSource
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createRemoteModule(baseURL: String) = module {
    factory<Interceptor> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiClient::class.java)
    }

    single {
        UsersRemoteDataSource(get())
    }

    single {
        AlbumsRemoteDataSource(get())
    }

}