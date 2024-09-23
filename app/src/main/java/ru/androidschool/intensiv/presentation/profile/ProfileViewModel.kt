package ru.androidschool.intensiv.presentation.profile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.interactor.ProfileInteractor
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenAction
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenEffect
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenModel
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenState
import ru.androidschool.intensiv.presentation.BaseViewModel
import ru.androidschool.intensiv.presentation.watchlist.MoviePreviewItem
import timber.log.Timber

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor
) : BaseViewModel<ProfileScreenState, ProfileScreenEffect, ProfileScreenAction>() {

    private val exceptionHandler by lazy { CoroutineExceptionHandler { _, e -> handleError(e) } }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            mutableEffect.send(ProfileScreenEffect.ShowMessage(R.string.default_error))
        }
    }

    override fun handleAction(action: ProfileScreenAction) {
        when (action) {
            ProfileScreenAction.Load -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch(scope + exceptionHandler) {
            val movies = profileInteractor.getFavoriteMovies()
            mutableState.value = ProfileScreenState.Content(
                ProfileScreenModel(
                    favorites = movies.map { MoviePreviewItem(it) { navigateMovieDetail(it) } },
                    watch = emptyList(),
                    tabNames = listOf(
                        movies.size to MovieFinderApp.instance.getString(R.string.profile_tab_favorite),
                        0 to MovieFinderApp.instance.getString(R.string.profile_tab_later)
                    )
                )
            )
        }
    }

    private fun navigateMovieDetail(movie: Movie) {
        viewModelScope.launch {
            mutableEffect.send(
                ProfileScreenEffect.NavigateToMovieDetail(MovieDetailsArgs(movie.id, movie.isMovie))
            )
        }
    }
}
