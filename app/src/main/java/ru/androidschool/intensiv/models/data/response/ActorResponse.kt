package ru.androidschool.intensiv.models.data.response

import com.google.gson.annotations.SerializedName

data class ActorResponse(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)
