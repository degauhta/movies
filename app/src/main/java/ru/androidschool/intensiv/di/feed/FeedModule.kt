package ru.androidschool.intensiv.di.feed

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.converter.MovieResponseConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.data.repository.FeedRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.FeedInteractor
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.presentation.converters.MovieConverter

@Module
class FeedModule {

    @Provides
    fun provideFeedInteractor(feedRepository: FeedRepository): FeedInteractor {
        return FeedInteractor(feedRepository)
    }

    @Provides
    fun provideMovieConverter(): MovieConverter {
        return MovieConverter()
    }

    @Provides
    fun provideFeedRepository(
        apiClient: MovieApiInterface,
        database: MovieDatabase
    ): FeedRepository {
        return FeedRepositoryImpl(
            apiClient = apiClient,
            feedDao = database.feedDao,
            movieDao = database.movieDao,
            converter = MovieResponseConverter(),
            movieDaoConverter = MovieEntityDbConverter()
        )
    }
}
