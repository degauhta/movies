package ru.androidschool.intensiv.di.profile

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.repository.ProfileRepositoryImpl
import ru.androidschool.intensiv.domain.interactor.ProfileInteractor
import ru.androidschool.intensiv.domain.repository.ProfileRepository

@Module
class ProfileModule {

    @Provides
    fun provideProfileInteractor(profileRepository: ProfileRepository): ProfileInteractor {
        return ProfileInteractor(profileRepository)
    }

    @Provides
    fun provideProfileRepository(database: MovieDatabase): ProfileRepository {
        return ProfileRepositoryImpl(
            favoriteDao = database.favoriteDao,
            movieDaoConverter = MovieEntityDbConverter()
        )
    }
}
