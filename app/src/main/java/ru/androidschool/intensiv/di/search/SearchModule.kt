package ru.androidschool.intensiv.di.search

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.converter.MovieResponseConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.data.repository.SearchRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.SearchInteractor
import ru.androidschool.intensiv.domain.repository.SearchRepository

@Module
class SearchModule {

    @Provides
    fun provideSearchInteractor(searchRepository: SearchRepository): SearchInteractor {
        return SearchInteractor(searchRepository)
    }

    @Provides
    fun provideSearchRepository(
        database: MovieDatabase,
        apiClient: MovieApiInterface
    ): SearchRepository {
        return SearchRepositoryImpl(
            apiClient = apiClient,
            searchDao = database.searchDao,
            movieDao = database.movieDao,
            movieResponseConverter = MovieResponseConverter(),
            movieDaoConverter = MovieEntityDbConverter()
        )
    }
}
