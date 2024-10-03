package ru.androidschool.intensiv.di.tvshow

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.converter.TvShowEntityDbConverter
import ru.androidschool.intensiv.data.converter.TvShowResponseConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.data.repository.TvShowsRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.TvShowInteractor
import ru.androidschool.intensiv.domain.repository.TvShowsRepository
import ru.androidschool.intensiv.presentation.converters.TvShowConverter

@Module
class TvShowModule {

    @Provides
    fun provideTvShowInteractor(tvShowsRepository: TvShowsRepository): TvShowInteractor {
        return TvShowInteractor(tvShowsRepository)
    }

    @Provides
    fun provideTvShowConverter(): TvShowConverter {
        return TvShowConverter()
    }

    @Provides
    fun provideTvShowsRepository(
        apiClient: MovieApiInterface,
        database: MovieDatabase
    ): TvShowsRepository {
        return TvShowsRepositoryImpl(
            apiClient = apiClient,
            tvShowDao = database.tvShowDao,
            tvShowFeedDao = database.tvShowFeedDao,
            tvShowResponseConverter = TvShowResponseConverter(),
            tvShowEntityDbConverter = TvShowEntityDbConverter()
        )
    }
}
