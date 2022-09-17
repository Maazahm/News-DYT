package com.news_dyt.di

import android.util.Log
import com.news_dyt.retrofit.RetrofitAPI
import com.news_dyt.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// This is how Module is defined in Dagger
@Module
class NetworkModule {

    // This function is 'Provides method' and it returns Retrofit object
    // It is annotated by Provides as we want Dagger to provide Retrofit object
    // We want only one Retrofit object throughout our application. Hence,
    // the function is annotated with Singleton
    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        Log.d(Constants.TAG, "NetMod: pr ${Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()}")
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides implementation for our Retrofit Interface
    // It takes retrofit object and returns RetrofitAPI
    // Dagger will check if it can provide Retrofit object and will call above function
    // We want only one object of our API throughout the application. Hence, it
    // is annotated with Singleton
    @Singleton
    @Provides
    fun providesRetrofitAPI(retrofit: Retrofit) : RetrofitAPI {
        // Retrofit will see this Interface and will provide body to methods present
        // in the API
        Log.d(Constants.TAG, "NetMod: pra ${retrofit.create(RetrofitAPI::class.java)}")
        return retrofit.create(RetrofitAPI::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideNewsRepository(): NewsRepository {
//        return NewsRepository()
//    }
}