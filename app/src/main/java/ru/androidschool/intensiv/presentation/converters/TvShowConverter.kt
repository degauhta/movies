package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.tvshows.TvShowItem

class TvShowConverter {

    fun convert(movieResponse: MoviesResponse, onClick: (item: Movie) -> Unit): List<TvShowItem> {
        return movieResponse.results.map {
            val tvShow = Movie(
                id = it.id,
                title = it.name,
                overview = it.overview,
                voteAverage = it.voteAverage,
                imageUrl = it.posterPath.orEmpty()
            )
            TvShowItem(movie = tvShow, onClick = onClick)
        }
    }
}