package ru.androidschool.intensiv.di.search

import dagger.Component
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.domain.interactor.SearchInteractor

@Component(modules = [SearchModule::class], dependencies = [CoreComponent::class])
@SearchScope
interface SearchInnerApi {

    val searchInteractor: SearchInteractor
}
