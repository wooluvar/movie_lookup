package com.vinod.movie

import android.app.Application
import com.vinod.core.networkModule
import com.vinod.di.movieModule
import com.vinod.movielookup.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    networkModule,
                    movieModule,
                    viewModelModule
                )
            )
        }
    }
}