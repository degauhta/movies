package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.data.database.FeedDbEntity
import ru.androidschool.intensiv.models.data.database.MovieDbEntity

@Dao
interface FeedDao {

    @Transaction
    fun updateFeed(entities: List<FeedDbEntity>, type: String) {
        deleteMovies(type)
        insertMovies(entities)
    }

    @Query("DELETE FROM ${MovieDatabaseContract.FEED_TABLE_NAME} WHERE ${MovieDatabaseContract.FEED_COLUMN_TYPE}=:type")
    fun deleteMovies(type: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(entities: List<FeedDbEntity>)

    @Query(
        "SELECT ${MovieDatabaseContract.FEED_TABLE_NAME}.${MovieDatabaseContract.FEED_COLUMN_TYPE}, ${MovieDatabaseContract.MOVIES_TABLE_NAME}.* " +
                "FROM ${MovieDatabaseContract.FEED_TABLE_NAME} JOIN ${MovieDatabaseContract.MOVIES_TABLE_NAME} ON " +
                "${MovieDatabaseContract.FEED_TABLE_NAME}.${MovieDatabaseContract.FEED_COLUMN_ID} = " +
                "${MovieDatabaseContract.MOVIES_TABLE_NAME}.${MovieDatabaseContract.MOVIES_COLUMN_ID}"
    )
    fun getMovies(): Flow<Map<@MapColumn(columnName = MovieDatabaseContract.FEED_COLUMN_TYPE) String, List<MovieDbEntity>>>
}
