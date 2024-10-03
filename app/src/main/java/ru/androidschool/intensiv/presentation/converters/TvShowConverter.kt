package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.tvshows.TvShowItem

class TvShowConverter {

    fun convert(movies: List<Movie>, onClick: (item: Movie) -> Unit): List<TvShowItem> {
        return movies.map {
            TvShowItem(
                movie = it.copy(rating = it.rating.div(MovieConverter.HALF_IMDB_RATING)),
                onClick = onClick
            )
        }
    }
}
