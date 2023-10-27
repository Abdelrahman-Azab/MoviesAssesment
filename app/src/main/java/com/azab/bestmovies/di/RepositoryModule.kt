package com.azab.bestmovies.di

import com.azab.bestmovies.data.repository.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoviesRepository(get()) }
}