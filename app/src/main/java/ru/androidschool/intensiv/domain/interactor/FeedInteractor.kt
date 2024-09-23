package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes

class FeedInteractor(
    private val feedRepository: FeedRepository
) {

    suspend fun loadFeed(): Flow<Map<MovieTypes, List<Movie>>> {
        return feedRepository.loadFeed()
    }

    suspend fun updateFeedData() {
        feedRepository.updateFeedData()
    }
}
