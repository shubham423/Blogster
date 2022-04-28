package com.example.blogster.data.remote.requests


import com.example.blogster.data.remote.responses.SignUpResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "user")
    val user: SignUpResponse
)