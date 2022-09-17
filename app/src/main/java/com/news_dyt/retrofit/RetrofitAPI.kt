package com.news_dyt.retrofit

import com.news_dyt.models.News
import com.news_dyt.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    // Get request using EndPoints
    // An abstract function whose implementation will be provided by Retrofit
    // In getNews() function we are asking data from server through get request
    // We use coroutine and hence the suspend keyword
    // The return type of this function will be 'Response' and it will return news object
    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getNews(@Query("country") country: String = "in", @Query("page") page: Int = 1,
                        @Query("pageSize") pageSize: Int = 20) : Response<News>
}