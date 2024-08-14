package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.remote.MovieApiClient

object MovieRepository {

    private val api = MovieApiClient
    private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    private const val MOVIE_PATH_KEY = "movie"
    private const val TV_PATH_KEY = "tv"

    fun getTopRatedMovies() = api.apiClient.getTopRatedMovies(apiKey = API_KEY)

    fun getPopularMovies() = api.apiClient.getPopularMovies(apiKey = API_KEY)

    fun getTopRatedTvShow() = api.apiClient.getTopRatedTvShow(apiKey = API_KEY)

    fun getMovieDetails(id: Int, isMovie: Boolean) = api.apiClient.getMovieDetails(
        apiKey = API_KEY,
        id = id,
        movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY
    )

    fun getMovieCredits(id: Int, isMovie: Boolean) = api.apiClient.getMovieCredits(
        apiKey = API_KEY,
        id = id,
        movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY
    )
}
