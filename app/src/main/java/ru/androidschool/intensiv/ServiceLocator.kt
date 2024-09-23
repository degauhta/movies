package ru.androidschool.intensiv

import ru.androidschool.intensiv.data.converter.CreditResponseConverter
import ru.androidschool.intensiv.data.converter.GenreResponseConverter
import ru.androidschool.intensiv.data.converter.MovieDetailsConverter
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.converter.MovieResponseConverter
import ru.androidschool.intensiv.data.converter.TvShowDetailsConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiClient
import ru.androidschool.intensiv.data.repository.FeedRepositoryImpl
import ru.androidschool.intensiv.data.repository.MovieDetailsRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.FeedInteractor
import ru.androidschool.intensiv.domain.interactor.MovieDetailsInteractor
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository

object ServiceLocator {

    private val api = MovieApiClient
    private val db = MovieDatabase.get(MovieFinderApp.instance.applicationContext)

    private fun provideFeedRepository(): FeedRepository =
        FeedRepositoryImpl(
            apiClient = api.apiClient,
            feedDao = db.feedDao,
            movieDao = db.movieDao,
            converter = MovieResponseConverter(),
            movieDaoConverter = MovieEntityDbConverter()
        )

    private fun provideMovieRepository(): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(
            apiClient = api.apiClient,
            favoriteDao = db.favoriteDao,
            movieDetailsDao = db.movieDetailsDao,
            genreResponseConverter = GenreResponseConverter(),
            creditResponseConverter = CreditResponseConverter(),
            movieDetailsConverter = MovieDetailsConverter(),
            tvShowDetailsConverter = TvShowDetailsConverter()
        )

    fun provideFeedInteractor() = FeedInteractor(provideFeedRepository())

    fun provideMovieInteractor() = MovieDetailsInteractor(provideMovieRepository())
}
