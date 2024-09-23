package ru.androidschool.intensiv.models.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

@Entity(MovieDatabaseContract.ACTOR_TABLE_NAME)
data class ActorDbEntity(
    @PrimaryKey @ColumnInfo(name = MovieDatabaseContract.ACTOR_COLUMN_ID) val id: Int,
    val name: String,
    val photoUrl: String?
)
