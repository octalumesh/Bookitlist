package com.app.bookitlist.data.models.response

data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val user: User
)