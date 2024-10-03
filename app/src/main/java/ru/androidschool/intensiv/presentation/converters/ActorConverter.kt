package ru.androidschool.intensiv.presentation.converters

import ru.androidschool.intensiv.models.domain.Actor
import ru.androidschool.intensiv.presentation.moviedetails.ActorItem

class ActorConverter {

    fun convert(actors: List<Actor>): List<ActorItem> =
        actors.map { ActorItem(it) }
}
