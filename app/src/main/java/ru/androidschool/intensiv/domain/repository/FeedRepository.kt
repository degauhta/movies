package ru.androidschool.intensiv.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes

interface FeedRepository {

    suspend fun loadFeed(): Flow<Map<MovieTypes, List<Movie>>>

    suspend fun updateFeedData()
}
