package com.example.blogster.data.remote.responses


import com.example.blogster.data.remote.models.Profile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "profile")
    val profile: Profile
)