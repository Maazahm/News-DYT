package com.news_dyt.di

import android.content.Context
import com.news_dyt.NewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Component is a connector between consumer and producer in Dagger
// NetworkModule class is added as module inside it
// Singleton is used because our NetworkModule contains few objects that are scoped
// (i.e., Annotated as Singleton)
@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {

    // Few field in MainActivity are injected using @Inject and whenever Dagger reaches
    // this component(i.e., ApplicationComponent) it will check on which fields @Inject
    // is used and will initialize those fields.

    fun inject(newsActivity: NewsActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }
}