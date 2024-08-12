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
                rating = it.voteAverage.div(MovieConverter.HALF_IMDB_RATING),
                imageUrl = it.posterPath.createImageUrl(),
                isMovie = false
            )
            TvShowItem(movie = tvShow, onClick = onClick)
        }
    }

    // todo move to domain converter
    private fun String?.createImageUrl() =
        this?.let { "${MovieConverter.POSTER_DEFAULT_PATH}$this" }
}
