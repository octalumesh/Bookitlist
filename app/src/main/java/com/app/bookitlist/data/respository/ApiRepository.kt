package com.app.bookitlist.data.respository

import com.app.bookitlist.data.api.ApiService
import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.base.BaseResponse
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.models.request.OTPRequest
import com.app.bookitlist.data.models.request.RegisterRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.models.response.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {

    // Authentication methods
    suspend fun login(request: LoginRequest): ApiResult<AuthResponse> {
        return safeApiCall {
            val task = request.task.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneNumber = request.phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = request.password.toRequestBody("text/plain".toMediaTypeOrNull())
            apiService.login(task, phoneNumber, password)
        }
    }

    // Authentication methods
    suspend fun otpVerification(request: OTPRequest): ApiResult<AuthResponse> {
        return safeApiCall {
            val task = request.task.toRequestBody("text/plain".toMediaTypeOrNull())
            val token = request.token.toRequestBody("text/plain".toMediaTypeOrNull())
            val otp = request.otp.toRequestBody("text/plain".toMediaTypeOrNull())
            apiService.otpVerification(task, token, otp)
        }
    }

    suspend fun register(
        request: RegisterRequest
    ): ApiResult<AuthResponse> {
        return safeApiCall {
            val task = request.task.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = request.email.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneNumber = request.phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = request.password.toRequestBody("text/plain".toMediaTypeOrNull())
            val firstName = request.firstName.toRequestBody("text/plain".toMediaTypeOrNull())
            val ref = request.ref.toRequestBody("text/plain".toMediaTypeOrNull())
            apiService.register(task, email, firstName, password, phoneNumber, ref)
        }
    }

    // User methods
    suspend fun getUserProfile(): ApiResult<User> {
        return safeApiCall {
            apiService.getUserProfile()
        }
    }

    suspend fun updateProfile(user: User): ApiResult<User> {
        return safeApiCall {
            apiService.updateProfile(user)
        }
    }

    // Generic methods for reusability
    suspend fun <T> get(
        endpoint: String,
        responseType: Class<T>
    ): ApiResult<T> {
        return safeApiCall {
            apiService.get(endpoint) as Response<BaseResponse<T>>
        }
    }

    suspend fun <T> post(
        endpoint: String,
        body: Any,
        responseType: Class<T>
    ): ApiResult<T> {
        return safeApiCall {
            apiService.post(endpoint, body) as Response<BaseResponse<T>>
        }
    }

    suspend fun <T> put(
        endpoint: String,
        body: Any,
        responseType: Class<T>
    ): ApiResult<T> {
        return safeApiCall {
            apiService.put(endpoint, body) as Response<BaseResponse<T>>
        }
    }

    suspend fun <T> delete(
        endpoint: String,
        responseType: Class<T>
    ): ApiResult<T> {
        return safeApiCall {
            apiService.delete(endpoint) as Response<BaseResponse<T>>
        }
    }

    // File upload
    suspend fun uploadFile(
        endpoint: String,
        file: MultipartBody.Part
    ): ApiResult<String> {
        return safeApiCall {
            apiService.uploadAvatar(file)
        }
    }

    // Pagination support
    suspend fun <T> getPaginatedData(
        endpoint: String,
        page: Int,
        limit: Int,
        responseType: Class<T>
    ): ApiResult<List<T>> {
        return safeApiCall {
            apiService.getPosts(page, limit) as Response<BaseResponse<List<T>>>
        }
    }
}