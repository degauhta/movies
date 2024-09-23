package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(
    tableName = MovieDatabaseContract.FAVORITE_TABLE_NAME,
    primaryKeys = [
        MovieDatabaseContract.FAVORITE_COLUMN_ID
    ]
)
data class FavoriteDbEntity(
    @ColumnInfo(name = MovieDatabaseContract.FAVORITE_COLUMN_ID) val id: Int,
    val isMovie: Boolean
)
