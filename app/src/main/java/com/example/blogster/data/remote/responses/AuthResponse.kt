package com.example.blogster.data.remote.responses


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("user")
    val user: User
)