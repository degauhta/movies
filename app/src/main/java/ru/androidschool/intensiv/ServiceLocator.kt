package ru.androidschool.intensiv

import ru.androidschool.intensiv.domain.interactor.FeedInteractor

object ServiceLocator {

    fun provideFeedInteractor() = FeedInteractor()
}
