package ru.androidschool.intensiv.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val imageUrl: String?,
    val isMovie: Boolean
) : Parcelable
