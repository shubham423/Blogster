
package com.example.blogster.data.repository

import android.util.Log
import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.api.ConduitAuthApi
import com.example.blogster.data.remote.responses.*
import com.example.blogster.di.AppModule
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ConduitApi,private val conduitAuthApi: ConduitAuthApi) {

    suspend fun loginUser(email: String,password: String):Response<UserResponse> {
        return  api.loginUser(LoginRequest(LoginData(email, password)))
    }

    suspend fun signUpUser(email: String,password: String,username: String): Response<UserResponse>{
        return api.signUpUser(SignupRequest(SignupData(email,password,username)))
    }

    suspend fun getGlobalFeed(): Response<ArticlesResponse> {
        return api.getArticles()
    }

    suspend fun getMyFeed(token: String): Response<ArticlesResponse> {
        AppModule.authToken=token
        Log.d("MainRepo", token)
        return conduitAuthApi.getFeedArticles()
    }

    suspend fun updateUser(
        bio: String?,
        username: String?,
        image: String?,
        email: String?,
        password: String?
    ): Response<UserResponse> {

        return conduitAuthApi.updateCurrentUser(UserUpdateRequest(UserUpdateData(
            bio, email, image, username, password
        )))
    }

    suspend fun getCurrentUser(token: String): Response<UserResponse> {
        AppModule.authToken = token
        return conduitAuthApi.getCurrentUser()
    }

    suspend fun getFavoriteArticles(token: String,username: String): Response<ArticlesResponse> {
        AppModule.authToken = token
        return conduitAuthApi.getFavoriteArticles(username)
    }

    suspend fun getMyArticles(token: String,username: String): Response<ArticlesResponse> {
        AppModule.authToken = token
        return conduitAuthApi.getMyArticle(username)
    }

    suspend fun createArticle(articleCreateRequest: ArticleCreateRequest,token: String): Response<ArticleResponse>{
        AppModule.authToken = token
        return conduitAuthApi.createArticle(articleCreateRequest)
    }

    suspend fun getArticle(articleId: String): Response<ArticleResponse>{
        return api.getArticleBySlug(articleId)
    }




}