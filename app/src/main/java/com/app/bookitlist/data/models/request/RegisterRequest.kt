package com.app.bookitlist.data.models.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)