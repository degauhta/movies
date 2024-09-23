package ru.androidschool.intensiv.models.presentation.profile

import androidx.annotation.StringRes
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs

sealed interface ProfileScreenEffect {

    data class NavigateToMovieDetail(val args: MovieDetailsArgs) : ProfileScreenEffect

    data class ShowMessage(@StringRes val stringRes: Int) : ProfileScreenEffect
}
