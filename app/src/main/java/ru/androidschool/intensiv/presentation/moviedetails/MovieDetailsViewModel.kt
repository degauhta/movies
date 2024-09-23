package ru.androidschool.intensiv.presentation.moviedetails

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.interactor.MovieDetailsInteractor
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenAction
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenEffect
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenModel
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenState
import ru.androidschool.intensiv.presentation.BaseViewModel
import ru.androidschool.intensiv.presentation.converters.ActorConverter
import ru.androidschool.intensiv.presentation.converters.MovieConverter
import timber.log.Timber

class MovieDetailsViewModel(
    private val interactor: MovieDetailsInteractor,
    private val actorConverter: ActorConverter
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsScreenEffect, MovieDetailsScreenAction>() {

    private val exceptionHandler by lazy { CoroutineExceptionHandler { _, e -> handleError(e) } }

    private var args: MovieDetailsArgs? = null

    override fun handleAction(action: MovieDetailsScreenAction) {
        when (action) {
            is MovieDetailsScreenAction.Load -> fetchData(action.args)
            is MovieDetailsScreenAction.FavoriteClick -> handleFavoriteClick(action.isChecked)
        }
    }

    private fun fetchData(args: MovieDetailsArgs?) {
        if (args != null) {
            this.args = args
            viewModelScope.launch(scope + exceptionHandler) {
                interactor.updateGenres(args.id, args.isMovie)
                interactor.updateActors(args.id, args.isMovie)
            }
            viewModelScope.launch(scope + exceptionHandler) {
                interactor.getMovieFlow(args.id, args.isMovie).distinctUntilChanged().collect {
                    mutableState.value = MovieDetailsScreenState.Content(
                        MovieDetailsScreenModel(
                            movie = it.copy(rating = it.rating.div(MovieConverter.HALF_IMDB_RATING)),
                            genre = it.genres,
                            actors = actorConverter.convert(it.actors)
                        )
                    )
                }
            }
        } else {
            Timber.e("Failed to getParcelable MovieDetailsArgs")
            viewModelScope.launch {
                mutableEffect.send(MovieDetailsScreenEffect.ShowMessage(R.string.default_error))
            }
        }
    }

    private fun handleFavoriteClick(isChecked: Boolean) {
        viewModelScope.launch(scope + exceptionHandler) {
            args?.let {
                interactor.updateFavoriteStatus(it.id, isChecked, it.isMovie)
            }
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            mutableEffect.send(MovieDetailsScreenEffect.ShowMessage(R.string.load_update_error))
        }
    }
}
