package com.azab.bestmovies.application

import android.app.Application
import com.azab.bestmovies.di.networkModule
import com.azab.bestmovies.di.repositoryModule
import com.azab.bestmovies.di.useCaseModule
import com.azab.bestmovies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BestMoviesApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BestMoviesApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    networkModule,
                    useCaseModule
                )
            )
        }
    }

}