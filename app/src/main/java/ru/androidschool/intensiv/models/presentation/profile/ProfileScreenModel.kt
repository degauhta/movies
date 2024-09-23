package ru.androidschool.intensiv.models.presentation.profile

import ru.androidschool.intensiv.presentation.watchlist.MoviePreviewItem

data class ProfileScreenModel(
    val favorites: List<MoviePreviewItem>,
    val watch: List<MoviePreviewItem>,
    val tabNames: List<Pair<Int, String>>
)
