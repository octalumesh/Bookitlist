package com.app.bookitlist.data.models.response

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String?
)