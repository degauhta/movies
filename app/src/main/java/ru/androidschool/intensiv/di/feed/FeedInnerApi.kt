package ru.androidschool.intensiv.di.feed

import dagger.Component
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.domain.interactor.FeedInteractor
import ru.androidschool.intensiv.presentation.converters.MovieConverter

@Component(modules = [FeedModule::class], dependencies = [CoreComponent::class])
@FeedScope
interface FeedInnerApi {

    val feedInteractor: FeedInteractor

    val movieConverter: MovieConverter
}
