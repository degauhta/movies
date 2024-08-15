package ru.androidschool.intensiv.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.data.response.MoviesResponse

interface MovieApiInterface {

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(): Single<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MoviesResponse>

    @GET("tv/top_rated")
    fun getTopRatedTvShow(): Single<MoviesResponse>

    @GET("{movie_type}/{id}")
    fun getMovieDetails(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int
    ): Single<DetailsResponse>

    @GET("{movie_type}/{id}/credits")
    fun getMovieCredits(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int
    ): Single<CreditsResponse>
}
