package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.androidschool.intensiv.models.data.database.TvShowDbEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(shows: List<TvShowDbEntity>)
}
