package com.news_dyt.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val description: String,
    val urlToImage: String,
    val url: String,
    val publishedAt: String,
    val content: String
)
