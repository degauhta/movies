package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.feed.MovieItem

class MovieConverter {

    fun convert(movieResponse: MoviesResponse, onClick: (item: Movie) -> Unit): List<MovieItem> {
        return movieResponse.results.map {
            val movie = Movie(
                id = it.id,
                title = it.name,
                overview = it.overview,
                rating = it.voteAverage.div(HALF_IMDB_RATING),
                imageUrl = it.posterPath.createImageUrl(),
                isMovie = true
            )
            MovieItem(movie = movie, onClick = onClick)
        }
    }

    // todo move to domain converter
    private fun String?.createImageUrl() = this?.let { "$POSTER_DEFAULT_PATH$this" }

    companion object {
        const val HALF_IMDB_RATING = 2
        const val POSTER_DEFAULT_PATH = "https://image.tmdb.org/t/p/w500"
    }
}
