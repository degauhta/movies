package ru.androidschool.intensiv.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.data.response.MoviesResponse

interface MovieApiInterface {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MoviesResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(): MoviesResponse

    @GET("search/movie")
    suspend fun getMovieByQuery(@Query("query") query: String): MoviesResponse

    @GET("{movie_type}/{id}")
    suspend fun getMovieDetails(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int
    ): DetailsResponse

    @GET("{movie_type}/{id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int
    ): CreditsResponse
}
