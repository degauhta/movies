package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.data.database.TvShowDbEntity
import ru.androidschool.intensiv.models.data.database.TvShowFeedDbEntity

@Dao
interface TvShowFeedDao {

    @Query(
        "SELECT ${MovieDatabaseContract.TV_SHOWS_TABLE_NAME}.* FROM ${MovieDatabaseContract.TV_SHOWS_FEED_TABLE_NAME} JOIN ${MovieDatabaseContract.TV_SHOWS_TABLE_NAME} ON " +
                "${MovieDatabaseContract.TV_SHOWS_FEED_TABLE_NAME}.${MovieDatabaseContract.TV_SHOWS_FEED_COLUMN_ID} = " +
                "${MovieDatabaseContract.TV_SHOWS_TABLE_NAME}.${MovieDatabaseContract.TV_SHOWS_COLUMN_ID}"
    )
    fun getTvShows(): Flow<List<TvShowDbEntity>>

    @Transaction
    suspend fun updateTvShows(shows: List<TvShowFeedDbEntity>) {
        deleteTvShows()
        insertTvShows(shows)
    }

    @Query("DELETE FROM ${MovieDatabaseContract.TV_SHOWS_FEED_TABLE_NAME}")
    suspend fun deleteTvShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(shows: List<TvShowFeedDbEntity>)
}
