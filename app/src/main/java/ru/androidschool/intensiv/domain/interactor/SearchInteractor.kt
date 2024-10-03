package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.domain.repository.SearchRepository
import ru.androidschool.intensiv.models.domain.Movie

class SearchInteractor(private val searchRepository: SearchRepository) {

    suspend fun searchMovieFlow(query: String): Flow<List<Movie>> {
        return searchRepository.searchMovieFlow(query)
    }
}
