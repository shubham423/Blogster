package com.example.blogster.data.remote.responses


import com.example.blogster.data.remote.models.Comment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentResponse(
    @Json(name = "comment")
    val comments: Comment
)