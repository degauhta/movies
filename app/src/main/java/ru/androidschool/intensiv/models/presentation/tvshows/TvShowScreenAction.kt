package ru.androidschool.intensiv.models.presentation.tvshows

sealed interface TvShowScreenAction {

    data object Load : TvShowScreenAction
}
