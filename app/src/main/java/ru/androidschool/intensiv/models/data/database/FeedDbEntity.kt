package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(
    tableName = MovieDatabaseContract.FEED_TABLE_NAME,
    primaryKeys = [
        MovieDatabaseContract.FEED_COLUMN_ID,
        MovieDatabaseContract.FEED_COLUMN_TYPE
    ]
)
data class FeedDbEntity(
    @ColumnInfo(name = MovieDatabaseContract.FEED_COLUMN_ID) val id: Int,
    @ColumnInfo(name = MovieDatabaseContract.FEED_COLUMN_TYPE) val type: String
)
