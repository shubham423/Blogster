
package com.example.blogster.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.blogster.MainActivity
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
}