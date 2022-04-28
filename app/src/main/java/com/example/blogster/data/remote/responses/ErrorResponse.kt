package com.example.blogster.data.remote.responses


import com.example.blogster.data.remote.models.Errors
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "errors")
    val errors: Errors
)