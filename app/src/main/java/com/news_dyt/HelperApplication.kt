package com.news_dyt

import android.app.Application
import android.util.Log
import com.news_dyt.di.ApplicationComponent
import com.news_dyt.di.DaggerApplicationComponent
import com.news_dyt.utils.Constants

class HelperApplication : Application() {

    // It is defined at application level because it contains few objects that are Singleton
    // for entire application
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        // Initialization
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        Log.d(Constants.TAG, "helper: $applicationComponent")
    }
}