package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.domain.Actor
import ru.androidschool.intensiv.presentation.moviedetails.ActorItem

class ActorConverter {

    fun convert(creditsResponse: CreditsResponse): MutableList<ActorItem> {
        val result = mutableListOf<ActorItem>()
        creditsResponse.cast.forEach { cast ->
            val actor = Actor(
                id = cast.id,
                name = cast.name,
                photoUrl = cast.profilePath.orEmpty()
            )
            result.add(ActorItem(actor))
        }
        return result
    }
}
