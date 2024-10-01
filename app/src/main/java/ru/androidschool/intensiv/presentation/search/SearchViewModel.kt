package ru.androidschool.intensiv.presentation.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.interactor.SearchInteractor
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.search.SearchScreenAction
import ru.androidschool.intensiv.models.presentation.search.SearchScreenEffect
import ru.androidschool.intensiv.models.presentation.search.SearchScreenState
import ru.androidschool.intensiv.presentation.BaseViewModel
import ru.androidschool.intensiv.presentation.watchlist.MoviePreviewItem
import timber.log.Timber

class SearchViewModel(
    private val interactor: SearchInteractor
) : BaseViewModel<SearchScreenState, SearchScreenEffect, SearchScreenAction>() {

    private val exceptionHandler by lazy { CoroutineExceptionHandler { _, e -> handleError(e) } }
    private var searchJob: Job? = null

    override fun handleAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.Search -> search(action.query)
        }
    }

    private fun search(query: String) {
        searchJob?.cancel()
        val contentIds = mutableListOf<Int>()
        mutableState.value = SearchScreenState.Loading
        searchJob = viewModelScope.launch(scope + exceptionHandler) {
            interactor.searchMovieFlow(query)
                .distinctUntilChanged()
                .onCompletion {
                    if (mutableState.value is SearchScreenState.Loading) {
                        mutableState.value = SearchScreenState.Empty
                    }
                }
                .collect { searchResult ->
                    if (searchResult.isNotEmpty()) {
                        val content = mutableListOf<MoviePreviewItem>()
                        searchResult.forEach { movie ->
                            if (movie.id !in contentIds) {
                                content.add(MoviePreviewItem(movie) { navigateMovieDetail(it) })
                                contentIds.add(movie.id)
                            }
                        }
                        mutableState.value = SearchScreenState.Content(content)
                    }
                }
        }
    }

    private fun navigateMovieDetail(movie: Movie) {
        viewModelScope.launch {
            mutableEffect.send(
                SearchScreenEffect.NavigateToMovieDetail(MovieDetailsArgs(movie.id, movie.isMovie))
            )
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            mutableEffect.send(SearchScreenEffect.ShowMessage(R.string.load_update_error))
        }
    }
}
