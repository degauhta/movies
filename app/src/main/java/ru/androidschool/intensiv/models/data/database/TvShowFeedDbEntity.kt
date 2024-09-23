package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(tableName = MovieDatabaseContract.TV_SHOWS_FEED_TABLE_NAME)
data class TvShowFeedDbEntity(
    @PrimaryKey @ColumnInfo(name = MovieDatabaseContract.TV_SHOWS_FEED_COLUMN_ID) val id: Int
)
