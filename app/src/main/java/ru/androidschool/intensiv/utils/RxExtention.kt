package ru.androidschool.intensiv.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.models.data.response.MoviesResponse
import timber.log.Timber

fun <T : Any> Single<T>.ioToMainTransform(): Single<T> = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun Single<MoviesResponse>.onErrorReturnEmptyList() = this.onErrorReturn { error ->
    Timber.e(error)
    MoviesResponse(0, 0, 0, emptyList())
}
