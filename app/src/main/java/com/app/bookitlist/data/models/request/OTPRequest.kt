package com.app.bookitlist.data.models.request

data class OTPRequest(
    val task: String = "otp-verification",
    val token: String = "",
    val otp: String = ""
)