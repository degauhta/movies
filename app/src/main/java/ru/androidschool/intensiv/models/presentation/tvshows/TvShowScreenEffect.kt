package ru.androidschool.intensiv.models.presentation.tvshows

import androidx.annotation.StringRes
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs

sealed interface TvShowScreenEffect {

    data class NavigateTvShowDetail(val args: MovieDetailsArgs) : TvShowScreenEffect

    data class ShowMessage(@StringRes val stringRes: Int) : TvShowScreenEffect
}
