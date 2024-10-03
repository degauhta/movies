package ru.androidschool.intensiv.models.domain

import androidx.annotation.StringRes
import ru.androidschool.intensiv.R

enum class MovieTypes(@StringRes val resId: Int) {
    TOP(R.string.recommended),
    POPULAR(R.string.popular),
    NOW_PLAYING(R.string.upcoming)
}
