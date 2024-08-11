package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.models.domain.Movie

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0f - x,
                imageUrl = ""
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getSerials() = mutableListOf<Movie>().apply {
        add(
            Movie(
                title = "Игра престолов",
                voteAverage = 8.9f,
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/3dd1ba5f-e610-459d-82dd-eee4eeb151ae/3840x"
            )
        )
        add(
            Movie(
                title = "Во все тяжкие",
                voteAverage = 7.6f,
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/f15260fa-77f8-4664-b775-8b19ba6023f0/3840x"
            )
        )
        add(
            Movie(
                title = "Доктор Хаус",
                voteAverage = 8.8f,
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/1c35d37b-2b41-41b8-9d29-c4818f855536/3840x"
            )
        )
        add(
            Movie(
                title = "Рик и Морти",
                voteAverage = 7.5f,
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/62a61c6d-a1f8-4990-b152-b69490e5cee4/3840x"
            )
        )
        add(
            Movie(
                title = "Пацаны",
                voteAverage = 7.4f,
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/e328edb5-6381-4a9a-954c-5aa4df64d553/3840x"
            )
        )
    }
}
