package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.models.data.database.ActorDbEntity
import ru.androidschool.intensiv.models.data.database.GenreDbEntity
import ru.androidschool.intensiv.models.data.database.MovieDetailsJoin
import ru.androidschool.intensiv.models.data.database.MovieWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.MovieWithGenreCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowDetailsJoin
import ru.androidschool.intensiv.models.data.database.TvShowWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowWithGenreCrossRef

@Dao
interface MovieDetailsDao {

    @Transaction
    @Query("SELECT * FROM ${MovieDatabaseContract.MOVIES_TABLE_NAME} WHERE ${MovieDatabaseContract.MOVIES_COLUMN_ID} = :id")
    fun getMovieFlow(id: Int): Flow<MovieDetailsJoin>

    @Transaction
    @Query("SELECT * FROM ${MovieDatabaseContract.TV_SHOWS_TABLE_NAME} WHERE ${MovieDatabaseContract.TV_SHOWS_COLUMN_ID} = :id")
    fun getTvShowFlow(id: Int): Flow<TvShowDetailsJoin>

    @Transaction
    suspend fun updateMovieGenre(genres: List<GenreDbEntity>, crossRefs: List<MovieWithGenreCrossRef>) {
        insertGenres(genres)
        insertGenresCross(crossRefs)
    }

    @Transaction
    suspend fun updateTvShowGenre(genres: List<GenreDbEntity>, crossRefs: List<TvShowWithGenreCrossRef>) {
        insertGenres(genres)
        insertTvShowCross(crossRefs)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresCross(crossRefs: List<MovieWithGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowCross(crossRefs: List<TvShowWithGenreCrossRef>)

    @Transaction
    suspend fun updateMovieActors(actors: List<ActorDbEntity>, crossRefs: List<MovieWithActorCrossRef>) {
        insertActors(actors)
        insertActorsCrossRefs(crossRefs)
    }

    @Transaction
    suspend fun updateTvShowActors(actors: List<ActorDbEntity>, crossRefs: List<TvShowWithActorCrossRef>) {
        insertActors(actors)
        insertTvShowCrossRefs(crossRefs)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorsCrossRefs(crossRefs: List<MovieWithActorCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowCrossRefs(crossRefs: List<TvShowWithActorCrossRef>)
}
