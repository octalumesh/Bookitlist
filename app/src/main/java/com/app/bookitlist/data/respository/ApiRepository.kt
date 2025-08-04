package com.app.bookitlist.data.respository

import com.app.bookitlist.data.api.ApiService
import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.base.BaseResponse
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.models.request.RegisterRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.models.response.User
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {

    // Authentication methods
    suspend fun login(email: String, password: String): ApiResult<AuthResponse> {
        return safeApiCall {
            apiService.login(LoginRequest(email, password))
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String
    ): ApiResult<AuthResponse> {
        return safeApiCall {
            apiService.register(RegisterRequest(name, email, password, phone))
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