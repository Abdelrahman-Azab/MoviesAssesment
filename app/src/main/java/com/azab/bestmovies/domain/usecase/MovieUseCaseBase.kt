package com.azab.bestmovies.domain.usecase

import androidx.paging.PagingData
import com.azab.bestmovies.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesUseCaseBase {
    suspend operator fun invoke(): Flow<PagingData<Movie>>
}