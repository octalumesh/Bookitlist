package com.app.bookitlist.data.models.request

data class RegisterRequest(
    val firstName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val ref: String,
    val task: String = "register"
)