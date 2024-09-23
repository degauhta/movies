package ru.androidschool.intensiv.domain.repository

import ru.androidschool.intensiv.models.domain.Movie

interface ProfileRepository {

    suspend fun getFavoriteMovies(): List<Movie>
}
