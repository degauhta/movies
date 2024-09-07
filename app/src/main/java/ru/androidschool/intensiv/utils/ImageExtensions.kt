package ru.androidschool.intensiv.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import ru.androidschool.intensiv.BuildConfig

fun ImageView.loadImage(imageUrl: String?, @DrawableRes placeholderResId: Int) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(placeholderResId)
        .into(this)
}

fun String?.createImageUrl() = this?.let { "${BuildConfig.TMDB_POSTER_PATH}$W_300$this" }

private const val W_300 = "w300"
