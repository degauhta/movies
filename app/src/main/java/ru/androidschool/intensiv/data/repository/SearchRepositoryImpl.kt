package ru.androidschool.intensiv.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.converter.MovieResponseConverter
import ru.androidschool.intensiv.data.database.MovieDao
import ru.androidschool.intensiv.data.database.SearchDao
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.SearchRepository
import ru.androidschool.intensiv.models.data.database.MovieDbEntity
import ru.androidschool.intensiv.models.domain.Movie

class SearchRepositoryImpl(
    private val apiClient: MovieApiInterface,
    private val searchDao: SearchDao,
    private val movieDao: MovieDao,
    private val movieResponseConverter: MovieResponseConverter,
    private val movieDaoConverter: MovieEntityDbConverter
) : SearchRepository {

    override suspend fun searchMovieFlow(query: String): Flow<List<Movie>> {
        return flow {
            emit(searchDao.getMovieSearchFlow(query))
            emitAll(getMovieByQueryFromNetwork(query))
        }.map {
            movieDaoConverter.convertMovies(it)
        }
    }

    private suspend fun getMovieByQueryFromNetwork(query: String): Flow<List<MovieDbEntity>> {
        return flow {
            val entities = movieResponseConverter.convert(apiClient.getMovieByQuery(query))
            movieDao.insertMovies(entities)
            emit(entities)
        }.catch {
            emit(emptyList())
        }
    }
}
