package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.feed.MovieItem
import ru.androidschool.intensiv.utils.createImageUrl

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

    companion object {
        const val HALF_IMDB_RATING = 2
    }
}
