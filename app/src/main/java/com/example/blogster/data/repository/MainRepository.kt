
package com.example.blogster.data.repository

import android.provider.ContactsContract
import android.util.Log
import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.requests.LoginRequest
import com.example.blogster.data.remote.requests.UserLogInCredentials
import com.example.blogster.data.remote.requests.UserSignUpCredentials
import com.example.blogster.data.remote.responses.AuthResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ConduitApi) {

    suspend fun loginUser(email: String,password: String): Response<AuthResponse> {
        return api.loginUser(UserLogInCredentials(email,password))
//        Log.d("repository","${userLogInCreds.email}")
    }

    suspend fun signUpUser(userSingUpCreds:UserSignUpCredentials): Response<AuthResponse>{
        return api.signUpUser(userSingUpCreds)
    }
}