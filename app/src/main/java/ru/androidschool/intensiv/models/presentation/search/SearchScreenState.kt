package ru.androidschool.intensiv.models.presentation.search

import ru.androidschool.intensiv.presentation.watchlist.MoviePreviewItem

sealed interface SearchScreenState {

    data object Loading : SearchScreenState

    data class Content(val movies: List<MoviePreviewItem>) : SearchScreenState

    data object Empty : SearchScreenState
}
