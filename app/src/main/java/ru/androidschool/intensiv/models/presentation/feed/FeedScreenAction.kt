package ru.androidschool.intensiv.models.presentation.feed

sealed interface FeedScreenAction {

    data object Load : FeedScreenAction
}
