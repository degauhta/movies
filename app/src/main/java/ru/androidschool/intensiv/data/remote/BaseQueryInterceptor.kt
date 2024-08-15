package ru.androidschool.intensiv.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import ru.androidschool.intensiv.BuildConfig

class BaseQueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.TMDB_API_KEY)
            .addQueryParameter(LANGUAGE_KEY, DEFAULT_LANGUAGE)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE_KEY = "language"
        private const val DEFAULT_LANGUAGE = "ru"
    }
}
