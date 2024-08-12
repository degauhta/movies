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
                photoUrl = cast.profilePath.createPhotoUrl()
            )
            result.add(ActorItem(actor))
        }
        return result
    }

    // todo move to domain converter
    private fun String?.createPhotoUrl() = this?.let { "$PHOTO_DEFAULT_PATH$this" }

    companion object {
        const val PHOTO_DEFAULT_PATH = "https://image.tmdb.org/t/p/w500"
    }
}
