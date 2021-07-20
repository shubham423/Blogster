package com.example.blogster.data.remote.api

import com.example.blogster.data.remote.requests.LoginRequest
import com.example.blogster.data.remote.requests.UserLogInCredentials
import com.example.blogster.data.remote.requests.UserSignUpCredentials
import com.example.blogster.data.remote.responses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConduitApi {

    @POST("users")
    suspend fun signUpUser(
        @Body userSingUpCreds:UserSignUpCredentials
    ): Response<AuthResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body userLogInCredentials: UserLogInCredentials
    ): Response<AuthResponse>
}