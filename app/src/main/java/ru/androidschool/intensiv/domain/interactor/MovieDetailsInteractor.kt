package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.androidschool.intensiv.models.domain.Movie

class MovieDetailsInteractor() {

    suspend fun updateGenres(id: Int, isMovie: Boolean) {

    }

    suspend fun updateActors(id: Int, isMovie: Boolean) {

    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean, isMovie: Boolean) {

    }

    suspend fun getMovieFlow(id: Int, isMovie: Boolean): Flow<Movie> {
        return emptyFlow()
    }
}
