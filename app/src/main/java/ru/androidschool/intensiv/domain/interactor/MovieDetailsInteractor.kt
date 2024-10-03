package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.models.domain.Movie

class MovieDetailsInteractor(
    private val repository: MovieDetailsRepository
) {

    suspend fun updateGenres(id: Int, isMovie: Boolean) {
        repository.updateMovieGenres(id, isMovie)
    }

    suspend fun updateActors(id: Int, isMovie: Boolean) {
        repository.updateMovieCredits(id, isMovie)
    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean, isMovie: Boolean) {
        repository.updateFavoriteStatus(id, isFavorite, isMovie)
    }

    suspend fun getMovieFlow(id: Int, isMovie: Boolean): Flow<Movie> {
        return repository.getMovieFlow(id, isMovie)
    }
}
