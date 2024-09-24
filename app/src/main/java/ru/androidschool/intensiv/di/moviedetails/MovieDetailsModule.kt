package ru.androidschool.intensiv.di.moviedetails

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.converter.CreditResponseConverter
import ru.androidschool.intensiv.data.converter.GenreResponseConverter
import ru.androidschool.intensiv.data.converter.MovieDetailsConverter
import ru.androidschool.intensiv.data.converter.TvShowDetailsConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.data.repository.MovieDetailsRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.MovieDetailsInteractor
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.presentation.converters.ActorConverter

@Module
class MovieDetailsModule {

    @Provides
    @MovieDetailsScope
    fun provideMovieDetailsInteractor(repo: MovieDetailsRepository): MovieDetailsInteractor {
        return MovieDetailsInteractor(repo)
    }

    @Provides
    @MovieDetailsScope
    fun provideMovieRepository(
        apiClient: MovieApiInterface,
        database: MovieDatabase
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(
            apiClient = apiClient,
            favoriteDao = database.favoriteDao,
            movieDetailsDao = database.movieDetailsDao,
            genreResponseConverter = GenreResponseConverter(),
            creditResponseConverter = CreditResponseConverter(),
            movieDetailsConverter = MovieDetailsConverter(),
            tvShowDetailsConverter = TvShowDetailsConverter()
        )
    }

    @Provides
    @MovieDetailsScope
    fun provideActorConverter() = ActorConverter()
}
