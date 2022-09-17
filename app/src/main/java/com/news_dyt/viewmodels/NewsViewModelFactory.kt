package com.news_dyt.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news_dyt.repository.NewsRepository
import com.news_dyt.utils.Constants
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(private val repository: NewsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(Constants.TAG, "NVMFactory: Started ${NewsViewModel(repository) as T}")
        return NewsViewModel(repository) as T
    }
}