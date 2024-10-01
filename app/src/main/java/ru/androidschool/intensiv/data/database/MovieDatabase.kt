package ru.androidschool.intensiv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.models.data.database.ActorDbEntity
import ru.androidschool.intensiv.models.data.database.FavoriteDbEntity
import ru.androidschool.intensiv.models.data.database.FeedDbEntity
import ru.androidschool.intensiv.models.data.database.GenreDbEntity
import ru.androidschool.intensiv.models.data.database.MovieDbEntity
import ru.androidschool.intensiv.models.data.database.MovieWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.MovieWithGenreCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowDbEntity
import ru.androidschool.intensiv.models.data.database.TvShowFeedDbEntity
import ru.androidschool.intensiv.models.data.database.TvShowWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowWithGenreCrossRef

@Database(
    version = 1,
    entities = [
        MovieDbEntity::class,
        TvShowDbEntity::class,
        FeedDbEntity::class,
        TvShowFeedDbEntity::class,
        ActorDbEntity::class,
        GenreDbEntity::class,
        FavoriteDbEntity::class,
        MovieWithGenreCrossRef::class,
        MovieWithActorCrossRef::class,
        TvShowWithGenreCrossRef::class,
        TvShowWithActorCrossRef::class
    ]
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val movieDetailsDao: MovieDetailsDao

    abstract val feedDao: FeedDao

    abstract val tvShowDao: TvShowDao

    abstract val tvShowFeedDao: TvShowFeedDao

    abstract val favoriteDao: FavoriteDao

    abstract val searchDao: SearchDao
}
