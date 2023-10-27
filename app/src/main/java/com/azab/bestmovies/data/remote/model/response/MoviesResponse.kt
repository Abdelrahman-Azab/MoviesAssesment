package com.azab.bestmovies.data.remote.model.response

import com.azab.bestmovies.data.remote.model.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search") val search: List<Movie>? = null,
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val response: String? = null,
    @SerializedName("Error") val error: String? = null
)
