package ru.androidschool.intensiv.models.presentation.moviedetail

import androidx.annotation.StringRes

interface MovieDetailsScreenEffect {

    data class ShowMessage(@StringRes val stringRes: Int) : MovieDetailsScreenEffect
}
