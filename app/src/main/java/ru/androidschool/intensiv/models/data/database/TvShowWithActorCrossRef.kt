package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [TvShowWithActorCrossRef.ACTOR_ID, TvShowWithActorCrossRef.TV_SHOW_ID])
data class TvShowWithActorCrossRef(
    @ColumnInfo(name = TV_SHOW_ID) val tvShowId: Int,
    @ColumnInfo(name = ACTOR_ID) val actorId: Int
) {
    companion object {
        const val TV_SHOW_ID = "tv_show_id"
        const val ACTOR_ID = "actor_id"
    }
}
