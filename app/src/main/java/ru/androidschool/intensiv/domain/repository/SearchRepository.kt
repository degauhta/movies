package ru.androidschool.intensiv.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.domain.Movie

interface SearchRepository {

    suspend fun searchMovieFlow(query: String): Flow<List<Movie>>
}
