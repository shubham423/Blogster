package com.example.blogster.data.remote.api

import com.example.blogster.data.remote.requests.LoginRequest
import com.example.blogster.data.remote.requests.SignupRequest
import com.example.blogster.data.remote.responses.*
import retrofit2.Response
import retrofit2.http.*

interface ConduitApi {

    @POST("users")
    suspend fun signUpUser(
        @Body userCreds: SignupRequest
    ): Response<UserResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body userCreds: LoginRequest
    ): Response<UserResponse>

    @GET("articles")
    suspend fun getArticles(
    ): Response<ArticlesResponse>

    @GET("articles/{slug}")
    suspend fun getArticleBySlug(
        @Path("slug") slug: String
    ): Response<ArticleResponse>
}