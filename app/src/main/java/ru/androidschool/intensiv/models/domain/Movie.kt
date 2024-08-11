package ru.androidschool.intensiv.models.domain

data class Movie(
    val title: String? = "",
    val voteAverage: Float = 0.0f,
    val imageUrl: String
) {
    val rating: Float
        get() = voteAverage.div(2)
}
