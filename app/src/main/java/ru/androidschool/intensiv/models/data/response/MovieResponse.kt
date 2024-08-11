package ru.androidschool.intensiv.models.data.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    @SerializedName(value = "name", alternate = ["title"])
    val name: String,
    val overview: String,
    @SerializedName(value = "release_date", alternate = ["first_air_date"])
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Float
) {

    @SerializedName("poster_path")
    val posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
