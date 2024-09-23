package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes

class FeedInteractor {

    suspend fun loadFeed(): Flow<Map<MovieTypes, List<Movie>>> {
        return emptyFlow()
    }

    suspend fun updateFeedData() = Unit
}
