package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(tableName = MovieDatabaseContract.MOVIES_TABLE_NAME)
data class MovieDbEntity(
    @PrimaryKey @ColumnInfo(name = MovieDatabaseContract.MOVIES_COLUMN_ID) val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Float,
    val posterPath: String
)
