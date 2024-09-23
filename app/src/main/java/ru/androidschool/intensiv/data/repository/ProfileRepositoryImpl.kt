package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.database.FavoriteDao
import ru.androidschool.intensiv.domain.repository.ProfileRepository
import ru.androidschool.intensiv.models.domain.Movie

class ProfileRepositoryImpl(
    private val favoriteDao: FavoriteDao,
    private val movieDaoConverter: MovieEntityDbConverter
) : ProfileRepository {

    override suspend fun getFavoriteMovies(): List<Movie> {
        val movies = movieDaoConverter.convertMovies(favoriteDao.getFavoriteMovies())
        val tvShows = movieDaoConverter.convertMovies(favoriteDao.getFavoriteTvShows(), false)
        return (movies + tvShows).shuffled()
    }
}
