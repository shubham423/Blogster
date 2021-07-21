
package com.example.blogster.data.repository

import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.responses.*
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ConduitApi) {

    suspend fun loginUser(email: String,password: String): User? {
        val response= api.loginUser(LoginRequest(LoginData(email, password)))
        return response.body()?.user
    }

    suspend fun signUpUser(email: String,password: String,username: String): User?{
        val response= api.signUpUser(SignupRequest(SignupData(email,password,username)))
        return response.body()?.user
    }
}