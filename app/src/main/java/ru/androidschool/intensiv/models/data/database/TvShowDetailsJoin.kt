package ru.androidschool.intensiv.models.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

data class TvShowDetailsJoin(
    @Embedded val movieEntity: TvShowDbEntity,
    @Relation(
        entity = FavoriteDbEntity::class,
        parentColumn = MovieDatabaseContract.TV_SHOWS_COLUMN_ID,
        entityColumn = MovieDatabaseContract.GENRE_COLUMN_ID
    )
    val favoriteEntity: FavoriteDbEntity?,
    @Relation(
        entity = GenreDbEntity::class,
        parentColumn = MovieDatabaseContract.TV_SHOWS_COLUMN_ID,
        entityColumn = MovieDatabaseContract.GENRE_COLUMN_ID,
        associateBy = Junction(
            value = TvShowWithGenreCrossRef::class,
            parentColumn = TvShowWithGenreCrossRef.TV_SHOW_ID,
            entityColumn = TvShowWithGenreCrossRef.GENRE_ID
        )
    )
    val genres: List<GenreDbEntity>,
    @Relation(
        entity = ActorDbEntity::class,
        parentColumn = MovieDatabaseContract.TV_SHOWS_COLUMN_ID,
        entityColumn = MovieDatabaseContract.ACTOR_COLUMN_ID,
        associateBy = Junction(
            value = TvShowWithActorCrossRef::class,
            parentColumn = TvShowWithActorCrossRef.TV_SHOW_ID,
            entityColumn = TvShowWithActorCrossRef.ACTOR_ID
        )
    )
    val actors: List<ActorDbEntity>
)
