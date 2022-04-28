package com.example.blogster.data.remote.responses


import com.example.blogster.data.remote.models.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "user")
    val user: User
)