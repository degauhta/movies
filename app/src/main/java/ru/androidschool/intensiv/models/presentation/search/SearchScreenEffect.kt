package ru.androidschool.intensiv.models.presentation.search

import androidx.annotation.StringRes
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs

sealed interface SearchScreenEffect {

    data class NavigateToMovieDetail(val detailsArgs: MovieDetailsArgs) : SearchScreenEffect

    data class ShowMessage(@StringRes val stringRes: Int) : SearchScreenEffect
}
