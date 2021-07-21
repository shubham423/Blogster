package com.example.blogster.data.remote.api

import com.example.blogster.data.remote.responses.LoginRequest
import com.example.blogster.data.remote.responses.SignupRequest
import com.example.blogster.data.remote.responses.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConduitApi {

    @POST("users")
    suspend fun signUpUser(
        @Body userCreds: SignupRequest
    ): Response<UserResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body userCreds: LoginRequest
    ): Response<UserResponse>
}