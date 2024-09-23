package ru.androidschool.intensiv.domain.interactor

import ru.androidschool.intensiv.domain.repository.ProfileRepository
import ru.androidschool.intensiv.models.domain.Movie

class ProfileInteractor(
    private val repository: ProfileRepository
) {
    suspend fun getFavoriteMovies(): List<Movie> {
        return repository.getFavoriteMovies()
    }
}
