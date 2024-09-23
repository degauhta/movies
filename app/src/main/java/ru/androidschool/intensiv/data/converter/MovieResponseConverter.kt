package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.MovieDbEntity
import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.utils.createImageUrl

class MovieResponseConverter {

    fun convert(response: MoviesResponse): List<MovieDbEntity> {
        return response.results.map {
            MovieDbEntity(
                id = it.id,
                title = it.name,
                overview = it.overview,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath.createImageUrl().orEmpty()
            )
        }
    }
}
