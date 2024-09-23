package ru.androidschool.intensiv.models.presentation.moviedetail

sealed interface MovieDetailsScreenState {

    data class Content(val screenModel: MovieDetailsScreenModel) : MovieDetailsScreenState
}
