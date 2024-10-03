package ru.androidschool.intensiv.di.core

import dagger.Component
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class, NetworkModule::class])
@Singleton
interface CoreComponent {

    val apiClient: MovieApiInterface

    val database: MovieDatabase
}
