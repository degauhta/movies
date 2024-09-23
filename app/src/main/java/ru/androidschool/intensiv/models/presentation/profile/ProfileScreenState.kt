package ru.androidschool.intensiv.models.presentation.profile

sealed interface ProfileScreenState {

    data class Content(val model: ProfileScreenModel) : ProfileScreenState
}
