package ru.androidschool.intensiv.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.data.response.MoviesResponse

interface MovieApiInterface {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<MoviesResponse>

    @GET("tv/top_rated")
    fun getTopRatedTvShow(
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<MoviesResponse>

    @GET("{movie_type}/{id}")
    fun getMovieDetails(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<DetailsResponse>

    @GET("{movie_type}/{id}/credits")
    fun getMovieCredits(
        @Path("movie_type") movieType: String,
        @Path("id") id: Int,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<CreditsResponse>

    private companion object {
        private const val DEFAULT_LANGUAGE = "ru"
    }
}
