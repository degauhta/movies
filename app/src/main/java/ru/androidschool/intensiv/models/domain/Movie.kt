package ru.androidschool.intensiv.models.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Float,
    val imageUrl: String
) {
    val rating: Float
        get() = voteAverage.div(2)
}
