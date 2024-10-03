package ru.androidschool.intensiv.models.presentation.moviedetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsArgs(
    val id: Int,
    val isMovie: Boolean
) : Parcelable
