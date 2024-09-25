package ru.androidschool.intensiv.di.tvshow

import dagger.Component
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.domain.interactor.TvShowInteractor
import ru.androidschool.intensiv.presentation.converters.TvShowConverter

@Component(modules = [TvShowModule::class], dependencies = [CoreComponent::class])
@TvShowScope
interface TvShowInnerApi {

    val tvShowInteractor: TvShowInteractor

    val tvShowConverter: TvShowConverter
}
