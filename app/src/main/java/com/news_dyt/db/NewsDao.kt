package com.news_dyt.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news_dyt.models.Article
import com.news_dyt.models.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewsMain(article: List<Article>)

    @Query("SELECT * FROM articles order by publishedAt desc")
    fun getNews() : LiveData<List<Article>>

    @Delete
    suspend fun deleteNews(article: Article)
}