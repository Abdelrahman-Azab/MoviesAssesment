package com.azab.bestmovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("imdbID") val imdbID: String? = null,
    @SerializedName("Type") val type: String? = null,
    @SerializedName("Poster") val poster: String? = null,
    @SerializedName("liked") var liked:Boolean?=false
)

