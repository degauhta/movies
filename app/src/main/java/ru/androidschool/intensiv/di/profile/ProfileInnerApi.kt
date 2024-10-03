package ru.androidschool.intensiv.di.profile

import dagger.Component
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.domain.interactor.ProfileInteractor

@Component(modules = [ProfileModule::class], dependencies = [CoreComponent::class])
@ProfileScope
interface ProfileInnerApi {

    val profileInteractor: ProfileInteractor
}
