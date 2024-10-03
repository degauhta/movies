package ru.androidschool.intensiv.models.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.androidschool.intensiv.data.database.MovieDatabaseContract

data class MovieDetailsJoin(
    @Embedded val movieEntity: MovieDbEntity,
    @Relation(
        entity = FavoriteDbEntity::class,
        parentColumn = MovieDatabaseContract.MOVIES_COLUMN_ID,
        entityColumn = MovieDatabaseContract.GENRE_COLUMN_ID
    )
    val favoriteEntity: FavoriteDbEntity?,
    @Relation(
        entity = GenreDbEntity::class,
        parentColumn = MovieDatabaseContract.MOVIES_COLUMN_ID,
        entityColumn = MovieDatabaseContract.GENRE_COLUMN_ID,
        associateBy = Junction(
            value = MovieWithGenreCrossRef::class,
            parentColumn = MovieWithGenreCrossRef.MOVIE_ID,
            entityColumn = MovieWithGenreCrossRef.GENRE_ID
        )
    )
    val genres: List<GenreDbEntity>,
    @Relation(
        entity = ActorDbEntity::class,
        parentColumn = MovieDatabaseContract.MOVIES_COLUMN_ID,
        entityColumn = MovieDatabaseContract.ACTOR_COLUMN_ID,
        associateBy = Junction(
            value = MovieWithActorCrossRef::class,
            parentColumn = MovieWithActorCrossRef.MOVIE_ID,
            entityColumn = MovieWithActorCrossRef.ACTOR_ID
        )
    )
    val actors: List<ActorDbEntity>
)
