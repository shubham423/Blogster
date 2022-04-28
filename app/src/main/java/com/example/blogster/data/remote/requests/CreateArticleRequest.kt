package com.example.blogster.data.remote.requests


import com.example.blogster.data.remote.models.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateArticleRequest(
    @Json(name = "article")
    val article: Article
)