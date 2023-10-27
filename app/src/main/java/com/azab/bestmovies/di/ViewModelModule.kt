package com.azab.bestmovies.di

import com.azab.bestmovies.presentation.main.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { MovieViewModel(get()) }
}