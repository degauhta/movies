package ru.androidschool.intensiv.di.core

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.database.MovieDatabaseContract
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Application) {

    @Provides
    @Singleton
    fun provideMovieDatabase(): MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovieDatabase::class.java,
            name = MovieDatabaseContract.DB_NAME
        ).build()
    }
}
