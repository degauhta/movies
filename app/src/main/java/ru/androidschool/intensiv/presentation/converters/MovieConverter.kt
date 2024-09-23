package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.feed.MovieItem

class MovieConverter {

    fun convert(movies: List<Movie>, onClick: (item: Movie) -> Unit): List<MovieItem> {
        return movies.map {
            MovieItem(movie = it.copy(rating = it.rating.div(HALF_IMDB_RATING)), onClick = onClick)
        }
    }

    companion object {
        const val HALF_IMDB_RATING = 2
    }
}
