
package com.example.blogster.data.repository

import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.responses.*
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ConduitApi) {

    suspend fun loginUser(email: String,password: String):Response<UserResponse> {
        return  api.loginUser(LoginRequest(LoginData(email, password)))
    }

    suspend fun signUpUser(email: String,password: String,username: String): Response<UserResponse>{
        return api.signUpUser(SignupRequest(SignupData(email,password,username)))
    }

    suspend fun getGlobalFeed(): Response<ArticlesResponse> {
        return api.getArticles()
    }
}