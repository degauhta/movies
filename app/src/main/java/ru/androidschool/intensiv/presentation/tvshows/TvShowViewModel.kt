package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.interactor.TvShowInteractor
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenAction
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenEffect
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenState
import ru.androidschool.intensiv.presentation.BaseViewModel
import ru.androidschool.intensiv.presentation.converters.TvShowConverter
import timber.log.Timber

class TvShowViewModel(
    private val interactor: TvShowInteractor,
    private val converter: TvShowConverter
) : BaseViewModel<TvShowScreenState, TvShowScreenEffect, TvShowScreenAction>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e -> error = e }
    private var error: Throwable? = null

    init {
        mutableState.value = TvShowScreenState.Loading
        viewModelScope.launch(scope + exceptionHandler) {
            interactor.updateTvShow()
        }
    }

    override fun handleAction(action: TvShowScreenAction) {
        when (action) {
            TvShowScreenAction.Load -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch(scope + exceptionHandler) {
            interactor.getTvShowFlow().collect { tvShows ->
                if (tvShows.isNotEmpty()) {
                    val data = converter.convert(tvShows) { openTvShowDetail(it) }
                    mutableState.value = TvShowScreenState.Content(data)
                }
                error?.let { handleError(it) }
            }
        }
    }

    private fun openTvShowDetail(tvShow: Movie) {
        viewModelScope.launch {
            mutableEffect.send(
                TvShowScreenEffect.NavigateTvShowDetail(MovieDetailsArgs(tvShow.id, tvShow.isMovie))
            )
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        val current = mutableState.value
        when {
            current is TvShowScreenState.Content -> viewModelScope.launch {
                mutableEffect.send(TvShowScreenEffect.ShowMessage(R.string.load_update_error))
            }

            else -> mutableState.value = TvShowScreenState.Error(
                R.string.load_data_error_title,
                R.string.load_data_error_subtitle
            )
        }
    }
}
