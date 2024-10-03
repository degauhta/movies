package ru.androidschool.intensiv.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.domain.Movie

interface MovieDetailsRepository {

    suspend fun updateMovieGenres(id: Int, isMovie: Boolean)

    suspend fun updateMovieCredits(id: Int, isMovie: Boolean)

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean, isMovie: Boolean)

    suspend fun getMovieFlow(id: Int, isMovie: Boolean): Flow<Movie>
}
