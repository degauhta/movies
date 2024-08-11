package ru.androidschool.intensiv.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig

object MovieApiClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor(PrettyJsonLogger()).apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    val apiClient: MovieApiInterface by lazy { retrofit.create(MovieApiInterface::class.java) }
}
