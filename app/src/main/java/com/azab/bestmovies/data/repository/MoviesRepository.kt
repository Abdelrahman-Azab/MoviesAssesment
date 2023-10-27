package com.azab.bestmovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.azab.bestmovies.data.dataSource.MoviesDataSource
import com.azab.bestmovies.data.remote.api.MoviesApi
import com.azab.bestmovies.data.remote.model.Movie
import com.azab.bestmovies.domain.repository.MoviesRepositoryBase
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val moviesApiService: MoviesApi
) : MoviesRepositoryBase {

    override suspend fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesDataSource(moviesApiService) }
        ).flow
    }
}
