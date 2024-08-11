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
                voteAverage = it.voteAverage,
                imageUrl = it.posterPath.orEmpty()
            )
            MovieItem(movie = movie, onClick = onClick)
        }
    }
}