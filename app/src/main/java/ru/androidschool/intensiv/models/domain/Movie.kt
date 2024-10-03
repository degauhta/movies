package ru.androidschool.intensiv.models.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val imageUrl: String?,
    val isMovie: Boolean,
    val isFavorite: Boolean,
    val actors: List<Actor>,
    val genres: List<Genre>
)
