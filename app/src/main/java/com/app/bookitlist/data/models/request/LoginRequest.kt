package com.app.bookitlist.data.models.request

data class LoginRequest(
    val task: String = "login",
    val phoneNumber: String,
    val password: String
)