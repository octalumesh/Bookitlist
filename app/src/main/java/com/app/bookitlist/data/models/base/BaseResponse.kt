package com.app.bookitlist.data.models.base

data class BaseResponse<T>(
    val success: Boolean = false,
    val msg: String = "",
    val token: String? = "",
    val data: T? = null,
    val error: String? = null,
    val code: Int = 0
)

// Generic API Result wrapper
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class TokenSuccess(val userToken: String) : ApiResult<Nothing>()
    data class Error(val exception: Throwable) : ApiResult<Nothing>()
    data class Loading(val isLoading: Boolean = true) : ApiResult<Nothing>()
}

// Network response wrapper
data class NetworkResponse<T>(
    val isSuccess: Boolean,
    val data: T? = null,
    val msg: String = "",
    val token: String = "",
    val errorCode: Int = 0
)