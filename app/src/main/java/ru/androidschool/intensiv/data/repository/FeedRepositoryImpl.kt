package ru.androidschool.intensiv.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.androidschool.intensiv.data.converter.MovieEntityDbConverter
import ru.androidschool.intensiv.data.converter.MovieResponseConverter
import ru.androidschool.intensiv.data.database.FeedDao
import ru.androidschool.intensiv.data.database.MovieDao
import ru.androidschool.intensiv.data.database.MovieDatabaseContract
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.models.data.database.FeedDbEntity
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes

class FeedRepositoryImpl(
    private val apiClient: MovieApiInterface,
    private val feedDao: FeedDao,
    private val movieDao: MovieDao,
    private val converter: MovieResponseConverter,
    private val movieDaoConverter: MovieEntityDbConverter
) : FeedRepository {

    override suspend fun loadFeed(): Flow<Map<MovieTypes, List<Movie>>> {
        return feedDao.getMovies().map {
            movieDaoConverter.convert(it)
        }
    }

    override suspend fun updateFeedData() {
        val top = converter.convert(apiClient.getTopRatedMovies())
        movieDao.insertMovies(top)

        val popular = converter.convert(apiClient.getPopularMovies())
        movieDao.insertMovies(popular)

        val nowPlaying = converter.convert(apiClient.getNowPlayingMovies())
        movieDao.insertMovies(nowPlaying)

        feedDao.updateFeed(
            top.map { FeedDbEntity(id = it.id, MovieDatabaseContract.FEED_TOP_TYPE) },
            MovieDatabaseContract.FEED_TOP_TYPE
        )
        feedDao.updateFeed(
            popular.map { FeedDbEntity(id = it.id, MovieDatabaseContract.FEED_POPULAR_TYPE) },
            MovieDatabaseContract.FEED_POPULAR_TYPE
        )
        feedDao.updateFeed(
            nowPlaying.map { FeedDbEntity(id = it.id, MovieDatabaseContract.FEED_NOW_PLAYING_TYPE) },
            MovieDatabaseContract.FEED_NOW_PLAYING_TYPE
        )
    }
}
