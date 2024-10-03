package ru.androidschool.intensiv.data.database

object MovieDatabaseContract {

    const val DB_NAME = "MovieDatabase"

    const val MOVIES_TABLE_NAME = "movies"
    const val MOVIES_COLUMN_ID = "id"
    const val MOVIES_COLUMN_TITLE = "title"

    const val FEED_TABLE_NAME = "feed"
    const val FEED_COLUMN_ID = "id"
    const val FEED_COLUMN_TYPE = "type"
    const val FEED_TOP_TYPE = "top"
    const val FEED_POPULAR_TYPE = "popular"
    const val FEED_NOW_PLAYING_TYPE = "now_playing"

    const val TV_SHOWS_TABLE_NAME = "tv_show"
    const val TV_SHOWS_COLUMN_ID = "id"

    const val TV_SHOWS_FEED_TABLE_NAME = "tv_show_feed"
    const val TV_SHOWS_FEED_COLUMN_ID = "id"

    const val FAVORITE_TABLE_NAME = "favorite"
    const val FAVORITE_COLUMN_ID = "id"

    const val GENRE_TABLE_NAME = "genres"
    const val GENRE_COLUMN_ID = "id"
    const val GENRE_COLUMN_NAME = "genre_name"

    const val ACTOR_TABLE_NAME = "actors"
    const val ACTOR_COLUMN_ID = "id"
}
