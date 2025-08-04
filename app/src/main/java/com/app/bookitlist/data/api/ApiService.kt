package com.app.bookitlist.data.api

import com.app.bookitlist.data.models.base.BaseResponse
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.models.request.RegisterRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.models.response.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {

    // Authentication endpoints
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<AuthResponse>>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<BaseResponse<AuthResponse>>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: String): Response<BaseResponse<AuthResponse>>

    @POST("auth/logout")
    suspend fun logout(): Response<BaseResponse<Any>>

    // User endpoints
    @GET("user/profile")
    suspend fun getUserProfile(): Response<BaseResponse<User>>

    @PUT("user/profile")
    suspend fun updateProfile(@Body user: User): Response<BaseResponse<User>>

    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(@Part avatar: MultipartBody.Part): Response<BaseResponse<String>>

    // Generic GET request
    @GET
    suspend fun get(@Url url: String): Response<BaseResponse<Any>>

    // Generic POST request
    @POST
    suspend fun post(@Url url: String, @Body body: Any): Response<BaseResponse<Any>>

    // Generic PUT request
    @PUT
    suspend fun put(@Url url: String, @Body body: Any): Response<BaseResponse<Any>>

    // Generic DELETE request
    @DELETE
    suspend fun delete(@Url url: String): Response<BaseResponse<Any>>

    // File download
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

    // Pagination support
    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<BaseResponse<List<Any>>>
}