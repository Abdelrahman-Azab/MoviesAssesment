package com.azab.bestmovies.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azab.bestmovies.data.remote.api.MoviesApi
import com.azab.bestmovies.data.remote.model.Movie
import com.azab.bestmovies.utils.constants.Constants.ERROR_RESPONSE_MSG
import com.azab.bestmovies.utils.constants.Constants.SEARCH_INITIAL
import com.azab.bestmovies.utils.constants.Constants.SUCCESS_RESPONSE_MSG
import com.azab.bestmovies.utils.constants.Constants.TYPE_INITIAL

private const val STARTING_PAGE_INDEX = 1

class MoviesDataSource(
    private val moviesApi: MoviesApi
) : PagingSource<Int, Movie>() {



    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val page = params.key ?: 1
            val response =
                moviesApi.getMovies(SEARCH_INITIAL, TYPE_INITIAL, page)

            if (response.isSuccessful) {
                val movieResponse = response.body()
                if (movieResponse != null) {
                    if (movieResponse.response == SUCCESS_RESPONSE_MSG) {
                        val movies = movieResponse.search ?: emptyList()
                        return LoadResult.Page(
                            data = movies,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (movies.isEmpty()) null else page + 1
                        )
                    } else {
                        if (movieResponse.error == ERROR_RESPONSE_MSG) {
                            return LoadResult.Error(Exception(ERROR_RESPONSE_MSG))
                        } else {
                            return LoadResult.Error(Exception(movieResponse.error))
                        }

                    }
                }
            }

            return LoadResult.Error(Exception("API_FAILED ${response.code()}"))
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}