package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(tableName = MovieDatabaseContract.GENRE_TABLE_NAME)
data class GenreDbEntity(
    @PrimaryKey @ColumnInfo(name = MovieDatabaseContract.GENRE_COLUMN_ID) val id: Int,
    @ColumnInfo(name = MovieDatabaseContract.GENRE_COLUMN_NAME) val name: String
)
