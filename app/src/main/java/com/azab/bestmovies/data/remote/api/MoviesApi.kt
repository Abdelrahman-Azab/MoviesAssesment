package com.azab.bestmovies.data.remote.api

import com.azab.bestmovies.data.remote.ApiUrls.MOVIES
import com.azab.bestmovies.data.remote.model.response.MoviesResponse
import com.azab.bestmovies.utils.constants.Constants.PAGE
import com.azab.bestmovies.utils.constants.Constants.SEARCH
import com.azab.bestmovies.utils.constants.Constants.TYPE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET(MOVIES)
    suspend fun getMovies(
        @Query(SEARCH) search: String,
        @Query(TYPE) type: String,
        @Query(PAGE) page: Int
    ): Response<MoviesResponse>
}