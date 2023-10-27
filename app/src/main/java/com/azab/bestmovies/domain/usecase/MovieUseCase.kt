package com.azab.bestmovies.domain.usecase

import androidx.paging.PagingData
import com.azab.bestmovies.data.remote.model.Movie
import com.azab.bestmovies.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesUseCase (private val repository: MoviesRepository) :
    MoviesUseCaseBase {
    override suspend operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getMovies()
    }
}