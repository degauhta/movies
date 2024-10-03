package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.TvShowDetailsJoin
import ru.androidschool.intensiv.models.domain.Actor
import ru.androidschool.intensiv.models.domain.Genre
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.utils.createImageUrl

class TvShowDetailsConverter {

    fun convert(entity: TvShowDetailsJoin): Movie {
        return Movie(
            id = entity.movieEntity.id,
            title = entity.movieEntity.title,
            overview = entity.movieEntity.overview,
            rating = entity.movieEntity.voteAverage,
            imageUrl = entity.movieEntity.posterPath.createImageUrl(),
            isMovie = false,
            isFavorite = entity.favoriteEntity?.isMovie == false,
            genres = entity.genres.map { Genre(id = it.id, name = it.name) },
            actors = entity.actors.map { Actor(id = it.id, name = it.name, photoUrl = it.photoUrl) }
        )
    }
}
