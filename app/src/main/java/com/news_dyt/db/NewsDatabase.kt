package com.news_dyt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news_dyt.models.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getDao() : NewsDao
}