package ru.androidschool.intensiv.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Float,
    val imageUrl: String
) : Parcelable {

    val rating: Float
        get() = voteAverage.div(2)
}
