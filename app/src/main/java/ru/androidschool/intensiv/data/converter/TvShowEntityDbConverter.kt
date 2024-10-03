package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.TvShowDbEntity
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.utils.createImageUrl

class TvShowEntityDbConverter {

    fun convert(movies: List<TvShowDbEntity>): List<Movie> {
        return movies.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                rating = it.voteAverage,
                imageUrl = it.posterPath.createImageUrl(),
                isMovie = false,
                isFavorite = false,
                actors = emptyList(),
                genres = emptyList()
            )
        }
    }
}
