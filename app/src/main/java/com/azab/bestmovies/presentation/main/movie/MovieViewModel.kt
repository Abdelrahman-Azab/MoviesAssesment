package com.azab.bestmovies.presentation.main.movie


import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.azab.bestmovies.data.remote.model.Movie
import com.azab.bestmovies.domain.usecase.MoviesUseCase
import kotlinx.coroutines.flow.Flow

class MovieViewModel (
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {
    var likesCounter:Int=0

    suspend fun getMovies(): Flow<PagingData<Movie>> {
        return moviesUseCase.invoke()
    }

}