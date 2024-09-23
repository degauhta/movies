package ru.androidschool.intensiv.models.presentation.feed

import androidx.annotation.StringRes
import ru.androidschool.intensiv.models.domain.MovieTypes
import ru.androidschool.intensiv.presentation.feed.MovieItem

sealed interface FeedScreenState {

    data object Loading : FeedScreenState

    data class Error(@StringRes val title: Int, @StringRes val message: Int) : FeedScreenState

    data class Content(val data: Map<MovieTypes, List<MovieItem>>) : FeedScreenState
}
