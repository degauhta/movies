package ru.androidschool.intensiv.models.presentation.moviedetail

import ru.androidschool.intensiv.models.domain.Genre
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.moviedetails.ActorItem

data class MovieDetailsScreenModel(
    val movie: Movie,
    val genre: List<Genre>,
    val actors: List<ActorItem>
)
