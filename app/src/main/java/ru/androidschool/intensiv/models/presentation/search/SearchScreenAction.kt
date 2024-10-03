package ru.androidschool.intensiv.models.presentation.search

sealed interface SearchScreenAction {

    data class Search(val query: String) : SearchScreenAction
}
