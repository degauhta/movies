package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.tvshows.TvShowItem
import ru.androidschool.intensiv.utils.createImageUrl

class TvShowConverter {

    fun convert(movieResponse: MoviesResponse, onClick: (item: Movie) -> Unit): List<TvShowItem> {
        return movieResponse.results.map {
            val tvShow = Movie(
                id = it.id,
                title = it.name,
                overview = it.overview,
                rating = it.voteAverage.div(MovieConverter.HALF_IMDB_RATING),
                imageUrl = it.posterPath.createImageUrl(),
                isMovie = false,
                isFavorite = false,
                actors = emptyList(),
                genres = emptyList()
            )
            TvShowItem(movie = tvShow, onClick = onClick)
        }
    }
}
