package com.app.bookitlist.data.respository

import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.base.BaseResponse
import com.app.bookitlist.data.models.base.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

abstract class BaseRepository {

    // Generic API call handler
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<BaseResponse<T>>
    ): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        ApiResult.Success(body.data!!)
                    } else {
                        ApiResult.Error(Exception(body?.message ?: "Unknown error"))
                    }
                } else {
                    ApiResult.Error(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Timber.e(e, "API call failed")
                ApiResult.Error(e)
            }
        }
    }

    // Handle different response types
    suspend fun <T> handleApiResponse(
        response: Response<BaseResponse<T>>
    ): NetworkResponse<T> {
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    NetworkResponse(
                        isSuccess = true,
                        data = body.data,
                        message = body.message
                    )
                } else {
                    NetworkResponse(
                        isSuccess = false,
                        message = body?.message ?: "Unknown error",
                        errorCode = body?.code ?: 0
                    )
                }
            } else {
                NetworkResponse(
                    isSuccess = false,
                    message = "HTTP Error: ${response.code()}",
                    errorCode = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResponse(
                isSuccess = false,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}