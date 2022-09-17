package com.news_dyt.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news_dyt.db.NewsDatabase
import com.news_dyt.models.Article
import com.news_dyt.models.News
import com.news_dyt.retrofit.RetrofitAPI
import com.news_dyt.utils.Constants
import com.news_dyt.utils.Resource
import retrofit2.Response
import javax.inject.Inject

// Viewmodels will get data by accessing Retrofit API and/or Room Database through this
// Repository class
// Constructor injection is used so that Dagger can create the required object
class NewsRepository @Inject constructor(private val retrofitAPI: RetrofitAPI,
                                         private val newsDatabase: NewsDatabase) {

    private val _news = MutableLiveData<Resource<News>>()
    val  news: LiveData<Resource<News>>
    get() = _news

    private val _savedNews = MutableLiveData<List<Article>>()
    val  savedNews: LiveData<List<Article>>
        get() = _savedNews

    suspend fun getNews() {
        _news.postValue(Resource.Loading())
        val result = retrofitAPI.getNews()
        Log.d(Constants.TAG, "getNews result in newsrepo: $result")
        _news.postValue(handleNewsResponse(result))
        /*if (result.isSuccessful && result.body() != null){
            newsDatabase.getDao().addNews(result.body()!!)
            _news.postValue(result.body())
        }*/
    }

    private suspend fun handleNewsResponse(response: Response<News>) : Resource<News> {
        if (response.isSuccessful){
            response.body()?.let {
                resultResponse ->
                Log.d(Constants.TAG, "response in newsrepo: $resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Log.d(Constants.TAG, "error in newsrepo: ${response.message()}")
        return Resource.Error(response.message())
    }

    suspend fun insert(article: Article) = newsDatabase.getDao().addNews(article)

    fun getSavedNewsDB() = newsDatabase.getDao().getNews()

    suspend fun deleteArticle(article: Article) = newsDatabase.getDao().deleteNews(article)
}