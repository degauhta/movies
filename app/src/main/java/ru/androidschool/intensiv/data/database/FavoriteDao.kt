package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.androidschool.intensiv.models.data.database.FavoriteDbEntity
import ru.androidschool.intensiv.models.data.database.MovieDbEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: FavoriteDbEntity)

    @Delete
    fun deleteMovie(entity: FavoriteDbEntity)

    @Query(
        "SELECT * FROM ${MovieDatabaseContract.FAVORITE_TABLE_NAME} " +
                "JOIN ${MovieDatabaseContract.MOVIES_TABLE_NAME} " +
                "ON ${MovieDatabaseContract.FAVORITE_TABLE_NAME}.${MovieDatabaseContract.FAVORITE_COLUMN_ID} = " +
                "${MovieDatabaseContract.MOVIES_TABLE_NAME}.${MovieDatabaseContract.MOVIES_COLUMN_ID} " +
                "WHERE ${MovieDatabaseContract.FAVORITE_TABLE_NAME}.isMovie = 1 " +
                "UNION ALL " +
                "SELECT * FROM ${MovieDatabaseContract.FAVORITE_TABLE_NAME} " +
                "JOIN ${MovieDatabaseContract.TV_SHOWS_TABLE_NAME} " +
                "ON ${MovieDatabaseContract.FAVORITE_TABLE_NAME}.${MovieDatabaseContract.FAVORITE_COLUMN_ID} = " +
                "${MovieDatabaseContract.TV_SHOWS_TABLE_NAME}.${MovieDatabaseContract.TV_SHOWS_COLUMN_ID} " +
                "WHERE ${MovieDatabaseContract.FAVORITE_TABLE_NAME}.isMovie = 0"
    )
    fun getFavoriteMovies(): List<MovieDbEntity>
}
