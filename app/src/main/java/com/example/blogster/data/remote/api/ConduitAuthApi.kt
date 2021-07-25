package com.example.blogster.data.remote.api

import com.example.blogster.data.remote.responses.*
import retrofit2.Response
import retrofit2.http.*

interface ConduitAuthApi {

    @GET("user")
    suspend fun getCurrentUser(): Response<UserResponse>

    @PUT("user")
    suspend fun updateCurrentUser(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<UserResponse>

    @GET("profiles/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @POST("profiles/{username}/follow")
    suspend fun followProfile(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @DELETE("profiles/{username}/follow")
    suspend fun unfollowProfile(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @GET("articles/feed")
    suspend fun getFeedArticles(): Response<ArticlesResponse>

    @GET("articles/favorite")
    suspend fun getFavoriteArticle(
       @Query ("favorited") favorited: String
    ): Response<List<ArticleResponse>>

    @DELETE("articles/{slug}/favorite")
    suspend fun unfavoriteArticle(
        @Path("slug") slug: String
    ): Response<ArticleResponse>
}