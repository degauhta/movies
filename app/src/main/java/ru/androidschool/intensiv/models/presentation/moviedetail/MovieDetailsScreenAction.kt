package ru.androidschool.intensiv.models.presentation.moviedetail

sealed interface MovieDetailsScreenAction {

    data class Load(val args: MovieDetailsArgs?) : MovieDetailsScreenAction

    data class FavoriteClick(val isChecked: Boolean) : MovieDetailsScreenAction
}
