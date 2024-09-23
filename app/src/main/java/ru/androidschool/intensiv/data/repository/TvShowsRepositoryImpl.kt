package ru.androidschool.intensiv.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.androidschool.intensiv.data.converter.TvShowEntityDbConverter
import ru.androidschool.intensiv.data.converter.TvShowResponseConverter
import ru.androidschool.intensiv.data.database.TvShowDao
import ru.androidschool.intensiv.data.database.TvShowFeedDao
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.TvShowsRepository
import ru.androidschool.intensiv.models.data.database.TvShowFeedDbEntity
import ru.androidschool.intensiv.models.domain.Movie

class TvShowsRepositoryImpl(
    private val apiClient: MovieApiInterface,
    private val tvShowDao: TvShowDao,
    private val tvShowFeedDao: TvShowFeedDao,
    private val tvShowResponseConverter: TvShowResponseConverter,
    private val tvShowEntityDbConverter: TvShowEntityDbConverter
) : TvShowsRepository {

    override suspend fun getTvShowFlow(): Flow<List<Movie>> {
        return tvShowFeedDao.getTvShows().map { tvShowEntityDbConverter.convert(it) }
    }

    override suspend fun updateTvShow() {
        val top = tvShowResponseConverter.convert(apiClient.getTopRatedTvShow())
        tvShowDao.insertTvShows(top)

        tvShowFeedDao.updateTvShows(
            top.map { TvShowFeedDbEntity(it.id) }
        )
    }
}
