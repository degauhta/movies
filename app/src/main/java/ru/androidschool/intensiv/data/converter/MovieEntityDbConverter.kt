package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.data.database.MovieDatabaseContract
import ru.androidschool.intensiv.models.data.database.MovieDbEntity
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes
import ru.androidschool.intensiv.utils.createImageUrl

class MovieEntityDbConverter {

    fun convert(data: Map<String, List<MovieDbEntity>>): Map<MovieTypes, List<Movie>> {
        return data.map {
            it.key.toMovieTypes() to convertMovies(it.value)
        }.toMap()
    }

    fun convertMovies(movies: List<MovieDbEntity>): List<Movie> {
        return movies.map { convert(it) }
    }

    private fun convert(movie: MovieDbEntity) =
        Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            rating = movie.voteAverage,
            imageUrl = movie.posterPath.createImageUrl(),
            isMovie = true,
            isFavorite = false,
            genres = emptyList(),
            actors = emptyList()
        )

    private fun String.toMovieTypes(): MovieTypes = when (this) {
        MovieDatabaseContract.FEED_TOP_TYPE -> MovieTypes.TOP
        MovieDatabaseContract.FEED_POPULAR_TYPE -> MovieTypes.POPULAR
        else -> MovieTypes.NOW_PLAYING
    }
}
