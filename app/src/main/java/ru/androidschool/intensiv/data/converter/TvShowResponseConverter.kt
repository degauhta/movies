package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.TvShowDbEntity
import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.utils.createImageUrl

class TvShowResponseConverter {

    fun convert(response: MoviesResponse): List<TvShowDbEntity> {
        return response.results.map {
            TvShowDbEntity(
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
