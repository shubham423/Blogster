package com.example.blogster.data.remote.requests

import com.example.blogster.data.remote.responses.UserUpdateResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserUpdateRequest(
    @Json(name = "user")
    val user: UserUpdateResponse
)