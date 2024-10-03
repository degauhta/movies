package ru.androidschool.intensiv.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.domain.Movie

interface TvShowsRepository {

    suspend fun getTvShowFlow(): Flow<List<Movie>>

    suspend fun updateTvShow()
}
