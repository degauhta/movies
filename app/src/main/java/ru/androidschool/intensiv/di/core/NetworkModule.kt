package ru.androidschool.intensiv.di.core

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.remote.BaseQueryInterceptor
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.data.remote.PrettyJsonLogger
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BaseQueryInterceptor())
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

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): MovieApiInterface {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieApiInterface::class.java)
    }
}
