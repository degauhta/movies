package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.GenreDbEntity
import ru.androidschool.intensiv.models.data.response.DetailsResponse

class GenreResponseConverter {

    fun convert(response: DetailsResponse): List<GenreDbEntity> {
        return response.genres.map {
            GenreDbEntity(id = it.id, name = it.name)
        }
    }
}
