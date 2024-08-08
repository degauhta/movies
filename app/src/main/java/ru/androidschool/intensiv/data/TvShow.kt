package ru.androidschool.intensiv.data

data class TvShow(
    val title: String,
    val voteAverage: Float,
    val imageUrl: String
) {
    val rating: Float
        get() = voteAverage.div(2)
}
