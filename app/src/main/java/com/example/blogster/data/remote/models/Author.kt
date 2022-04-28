package com.example.blogster.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "bio")
    val bio: Any?,
    @Json(name = "following")
    val following: Boolean,
    @Json(name = "image")
    val image: String,
    @Json(name = "username")
    val username: String
)