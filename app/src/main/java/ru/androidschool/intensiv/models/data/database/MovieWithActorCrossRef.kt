package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [MovieWithActorCrossRef.ACTOR_ID, MovieWithActorCrossRef.MOVIE_ID])
data class MovieWithActorCrossRef(
    @ColumnInfo(name = MOVIE_ID) val movieId: Int,
    @ColumnInfo(name = ACTOR_ID) val actorId: Int
) {
    companion object {
        const val MOVIE_ID = "movie_id"
        const val ACTOR_ID = "actor_id"
    }
}
