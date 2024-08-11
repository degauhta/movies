package ru.androidschool.intensiv.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class PrettyJsonLogger : HttpLoggingInterceptor.Logger {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun log(message: String) {
        val trimMessage = message.trim()

        if ((trimMessage.startsWith("{") && trimMessage.endsWith("}"))
            || (trimMessage.startsWith("[") && trimMessage.endsWith("]"))
        ) {
            try {
                val prettyJson = gson.toJson(JsonParser.parseString(message))
                Timber.d(prettyJson)
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        } else {
            Timber.d(message)
        }
    }
}