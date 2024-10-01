package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.androidschool.intensiv.models.data.database.MovieDbEntity

@Dao
interface SearchDao {

    @Transaction
    @Query(
        "SELECT * FROM ${MovieDatabaseContract.MOVIES_TABLE_NAME} " +
                "WHERE ${MovieDatabaseContract.MOVIES_COLUMN_TITLE} LIKE '%' || :query || '%'"
    )
    fun getMovieSearchFlow(query: String): List<MovieDbEntity>
}
