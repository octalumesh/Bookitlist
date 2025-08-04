package com.app.bookitlist.data.models.request

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)