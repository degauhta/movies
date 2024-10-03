package ru.androidschool.intensiv.data.converter

import ru.androidschool.intensiv.models.data.database.ActorDbEntity
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.utils.createImageUrl

class CreditResponseConverter {

    fun convert(response: CreditsResponse): List<ActorDbEntity> {
        return response.cast.map {
            ActorDbEntity(id = it.id, name = it.name, photoUrl = it.profilePath.createImageUrl())
        }
    }
}
