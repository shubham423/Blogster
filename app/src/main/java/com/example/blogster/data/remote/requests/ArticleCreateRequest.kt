package com.example.blogster.data.remote.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleCreateRequest(
    @Json(name = "article")
    val article: Article
) {
    @JsonClass(generateAdapter = true)
    data class Article(
        @Json(name = "body")
        val body: String,
        @Json(name = "description")
        val description: String,
        @Json(name = "tagList")
        val tagList: List<String>,
        @Json(name = "title")
        val title: String
    )
}