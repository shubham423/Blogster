package com.example.blogster.data.remote.requests


import com.example.blogster.data.remote.responses.LoginResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "user")
    val user: LoginResponse
)