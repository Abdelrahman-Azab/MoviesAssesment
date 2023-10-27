package com.azab.bestmovies.domain.repository

import androidx.paging.PagingData
import com.azab.bestmovies.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow


interface MoviesRepositoryBase {
    suspend fun getMovies():  Flow<PagingData<Movie>>
}