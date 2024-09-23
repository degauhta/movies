package ru.androidschool.intensiv.models.presentation.tvshows

import androidx.annotation.StringRes
import ru.androidschool.intensiv.presentation.tvshows.TvShowItem

sealed interface TvShowScreenState {

    data object Loading : TvShowScreenState

    data class Error(@StringRes val title: Int, @StringRes val message: Int) : TvShowScreenState

    data class Content(val data: List<TvShowItem>) : TvShowScreenState
}
