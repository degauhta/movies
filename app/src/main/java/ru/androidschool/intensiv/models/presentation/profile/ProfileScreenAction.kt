package ru.androidschool.intensiv.models.presentation.profile

sealed interface ProfileScreenAction {

    data object Load : ProfileScreenAction
}
