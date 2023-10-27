package com.azab.bestmovies.di

import com.azab.bestmovies.domain.usecase.MoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { MoviesUseCase(get()) }
}