package com.news_dyt.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.news_dyt.db.NewsDatabase
import com.news_dyt.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(context : Context) : NewsDatabase {
        Log.d(Constants.TAG, "return in NDB: " +
                "${Room.databaseBuilder(context, NewsDatabase::class.java, "news.db").build()}")
        return Room.databaseBuilder(context, NewsDatabase::class.java, "news.db").build()
    }
}