package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [TvShowWithGenreCrossRef.TV_SHOW_ID, TvShowWithGenreCrossRef.GENRE_ID])
data class TvShowWithGenreCrossRef(
    @ColumnInfo(name = TV_SHOW_ID) val tvShowId: Int,
    @ColumnInfo(name = GENRE_ID) val genreId: Int
) {
    companion object {
        const val TV_SHOW_ID = "tv_show_id"
        const val GENRE_ID = "genre_id"
    }
}
