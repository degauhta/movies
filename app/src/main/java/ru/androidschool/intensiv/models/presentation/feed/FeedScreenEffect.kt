package ru.androidschool.intensiv.models.presentation.feed

import androidx.annotation.StringRes
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs

sealed interface FeedScreenEffect {

    data class ShowMessage(@StringRes val stringRes: Int) : FeedScreenEffect

    data class NavigateToMovieDetail(val args: MovieDetailsArgs) : FeedScreenEffect
}
