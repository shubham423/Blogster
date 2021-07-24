package com.example.blogster.data.remote.api

import com.example.blogster.data.remote.responses.ArticlesResponse
import com.example.blogster.data.remote.responses.LoginRequest
import com.example.blogster.data.remote.responses.SignupRequest
import com.example.blogster.data.remote.responses.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Query("author") author: String? = null,
        @Query("favourited") favourited: String? = null,
        @Query("tag") tag: String? = null
    ): Response<ArticlesResponse>
}