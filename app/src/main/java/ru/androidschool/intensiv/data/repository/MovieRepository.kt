package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.remote.MovieApiClient

object MovieRepository {

    private val api = MovieApiClient
    private const val MOVIE_PATH_KEY = "movie"
    private const val TV_PATH_KEY = "tv"

    fun getTopRatedMovies() = api.apiClient.getTopRatedMovies()

    fun getPopularMovies() = api.apiClient.getPopularMovies()

    fun getTopRatedTvShow() = api.apiClient.getTopRatedTvShow()

    fun getMovieDetails(id: Int, isMovie: Boolean) = api.apiClient.getMovieDetails(
        id = id,
        movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY
    )

    fun getMovieCredits(id: Int, isMovie: Boolean) = api.apiClient.getMovieCredits(
        id = id,
        movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY
    )
}
