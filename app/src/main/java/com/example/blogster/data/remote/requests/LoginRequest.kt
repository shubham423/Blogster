package com.example.blogster.data.remote.requests


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user")
    val userSignUp: UserLogInCredentials
)