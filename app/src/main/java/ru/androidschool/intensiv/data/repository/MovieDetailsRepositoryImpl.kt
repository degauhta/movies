package ru.androidschool.intensiv.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.androidschool.intensiv.data.converter.CreditResponseConverter
import ru.androidschool.intensiv.data.converter.GenreResponseConverter
import ru.androidschool.intensiv.data.converter.MovieDetailsConverter
import ru.androidschool.intensiv.data.converter.TvShowDetailsConverter
import ru.androidschool.intensiv.data.database.FavoriteDao
import ru.androidschool.intensiv.data.database.MovieDetailsDao
import ru.androidschool.intensiv.data.remote.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.models.data.database.FavoriteDbEntity
import ru.androidschool.intensiv.models.data.database.MovieWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.MovieWithGenreCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowWithActorCrossRef
import ru.androidschool.intensiv.models.data.database.TvShowWithGenreCrossRef
import ru.androidschool.intensiv.models.domain.Movie

class MovieDetailsRepositoryImpl(
    private val apiClient: MovieApiInterface,
    private val favoriteDao: FavoriteDao,
    private val movieDetailsDao: MovieDetailsDao,
    private val genreResponseConverter: GenreResponseConverter,
    private val creditResponseConverter: CreditResponseConverter,
    private val movieDetailsConverter: MovieDetailsConverter,
    private val tvShowDetailsConverter: TvShowDetailsConverter
) : MovieDetailsRepository {

    override suspend fun getMovieFlow(id: Int, isMovie: Boolean): Flow<Movie> {
        return if (isMovie) {
            movieDetailsDao.getMovieFlow(id).map { movieDetailsConverter.convert(it) }
        } else {
            movieDetailsDao.getTvShowFlow(id).map { tvShowDetailsConverter.convert(it) }
        }
    }

    override suspend fun updateMovieGenres(id: Int, isMovie: Boolean) {
        val response = apiClient.getMovieDetails(
            movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY,
            id = id
        )
        val genres = genreResponseConverter.convert(response)
        if (isMovie) {
            val crossRefs = genres.map { MovieWithGenreCrossRef(movieId = id, genreId = it.id) }
            movieDetailsDao.updateMovieGenre(genres, crossRefs)
        } else {
            val crossRefs = genres.map { TvShowWithGenreCrossRef(tvShowId = id, genreId = it.id) }
            movieDetailsDao.updateTvShowGenre(genres, crossRefs)
        }
    }

    override suspend fun updateMovieCredits(id: Int, isMovie: Boolean) {
        val response = apiClient.getMovieCredits(
            movieType = if (isMovie) MOVIE_PATH_KEY else TV_PATH_KEY,
            id = id
        )
        val actors = creditResponseConverter.convert(response)
        if (isMovie) {
            val crossRefs = actors.map { MovieWithActorCrossRef(movieId = id, actorId = it.id) }
            movieDetailsDao.updateMovieActors(actors, crossRefs)
        } else {
            val crossRefs = actors.map { TvShowWithActorCrossRef(tvShowId = id, actorId = it.id) }
            movieDetailsDao.updateTvShowActors(actors, crossRefs)
        }
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean, isMovie: Boolean) {
        if (isFavorite) {
            favoriteDao.insertMovie(FavoriteDbEntity(id, isMovie))
        } else {
            favoriteDao.deleteMovie(FavoriteDbEntity(id, isMovie))
        }
    }

    companion object {
        private const val MOVIE_PATH_KEY = "movie"
        private const val TV_PATH_KEY = "tv"
    }
}
