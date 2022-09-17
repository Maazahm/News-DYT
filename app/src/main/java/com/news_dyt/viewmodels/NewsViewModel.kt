package com.news_dyt.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news_dyt.models.Article
import com.news_dyt.models.News
import com.news_dyt.repository.NewsRepository
import com.news_dyt.utils.Constants.TAG
import com.news_dyt.utils.Resource
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    val newsLiveData : LiveData<Resource<News>>
    get() = newsRepository.news

    val savedNewsLiveData : LiveData<List<Article>>
        get() = newsRepository.savedNews

    var pageNumber = 1

    init {
        viewModelScope.launch {
            newsRepository.getNews()
            newsRepository.getSavedNewsDB()
            Log.d(TAG, "init in NVM: ${newsRepository.getNews()} " +
                    "${newsRepository.getSavedNewsDB()}")
        }
    }

    fun insert(article: Article) = viewModelScope.launch {
        Log.d(TAG, "insert: Started")
        Log.d(TAG, "insert: Started $article")
        Log.d(TAG, "NVM insert: newsLiveData $newsLiveData savedNewsLiveData $savedNewsLiveData")
        newsRepository.insert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        Log.d(TAG, "NVM: delete article : $article")
        newsRepository.deleteArticle(article)
    }
}