package ru.androidschool.intensiv.presentation.feed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.interactor.FeedInteractor
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenAction
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenEffect
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenState
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.presentation.BaseViewModel
import ru.androidschool.intensiv.presentation.converters.MovieConverter
import timber.log.Timber

class FeedViewModel(
    private val interactor: FeedInteractor,
    private val converter: MovieConverter
) : BaseViewModel<FeedScreenState, FeedScreenEffect, FeedScreenAction>() {

    private val exceptionHandler by lazy { CoroutineExceptionHandler { _, e -> error = e } }
    private var error: Throwable? = null

    init {
        mutableState.value = FeedScreenState.Loading
        viewModelScope.launch(scope + exceptionHandler) {
            interactor.updateFeedData()
        }
    }

    override fun handleAction(action: FeedScreenAction) {
        when (action) {
            FeedScreenAction.Load -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch(scope + exceptionHandler) {
            interactor.loadFeed().collect { data ->
                val content = mutableMapOf<MovieTypes, List<MovieItem>>().apply {
                    data[MovieTypes.TOP]?.takeIf { it.isNotEmpty() }?.let { movies ->
                        this[MovieTypes.TOP] = converter.convert(movies) { navigateMovieDetail(it) }
                    }
                    data[MovieTypes.POPULAR]?.takeIf { it.isNotEmpty() }?.let { movies ->
                        this[MovieTypes.POPULAR] =
                            converter.convert(movies) { navigateMovieDetail(it) }
                    }
                    data[MovieTypes.NOW_PLAYING]?.takeIf { it.isNotEmpty() }?.let { movies ->
                        this[MovieTypes.NOW_PLAYING] =
                            converter.convert(movies) { navigateMovieDetail(it) }
                    }
                }

                if (content.isNotEmpty()) {
                    mutableState.value = FeedScreenState.Content(content)
                }
                error?.let { handleError(it) }
            }
        }
    }

    private fun navigateMovieDetail(movie: Movie) {
        viewModelScope.launch {
            mutableEffect.send(
                FeedScreenEffect.NavigateToMovieDetail(MovieDetailsArgs(movie.id, movie.isMovie))
            )
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        val current = mutableState.value
        when {
            current is FeedScreenState.Content ->
                viewModelScope.launch {
                    mutableEffect.send(FeedScreenEffect.ShowMessage(R.string.load_update_error))
                }

            else -> mutableState.value = FeedScreenState.Error(
                R.string.load_data_error_title,
                R.string.load_data_error_subtitle
            )
        }
    }
}
