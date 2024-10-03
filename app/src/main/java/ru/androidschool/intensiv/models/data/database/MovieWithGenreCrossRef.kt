package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [MovieWithGenreCrossRef.MOVIE_ID, MovieWithGenreCrossRef.GENRE_ID])
data class MovieWithGenreCrossRef(
    @ColumnInfo(name = MOVIE_ID) val movieId: Int,
    @ColumnInfo(name = GENRE_ID) val genreId: Int
) {
    companion object {
        const val MOVIE_ID = "movie_id"
        const val GENRE_ID = "genre_id"
    }
}
