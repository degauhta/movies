package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.remote.MovieApiClient
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.data.response.MoviesResponse

object MovieRepository {

    private val api = MovieApiClient
    private const val MOVIE_PATH_KEY = "movie"
    private const val TV_PATH_KEY = "tv"

    fun getTopRatedMovies(): Single<MoviesResponse> = Single.error(Exception())

    fun getPopularMovies(): Single<MoviesResponse> = Single.error(Exception())

    fun getNowPlayingMovies(): Single<MoviesResponse> = Single.error(Exception())

    fun getTopRatedTvShow(): Single<MoviesResponse> = api.apiClient.getTopRatedTvShow()

    fun getMovieDetails(id: Int, isMovie: Boolean): Single<DetailsResponse> =
        Single.error(Exception())

    fun getMovieCredits(id: Int, isMovie: Boolean): Single<CreditsResponse> =
        Single.error(Exception())
}
