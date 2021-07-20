package com.example.blogster.data.remote.requests


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("user")
    val userSignUp: UserSignUpCredentials
)