package ru.androidschool.intensiv.di.moviedetails

import dagger.Component
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.domain.interactor.MovieDetailsInteractor
import ru.androidschool.intensiv.presentation.converters.ActorConverter

@Component(
    modules = [MovieDetailsModule::class],
    dependencies = [CoreComponent::class]
)
@MovieDetailsScope
interface MovieDetailsInnerApi {

    val movieDetailsInteractor: MovieDetailsInteractor

    val actorConverter: ActorConverter
}
