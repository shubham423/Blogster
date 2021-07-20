package com.example.blogster.data.remote.requests

import com.google.gson.annotations.SerializedName


data class UserSignUpCredentials(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
