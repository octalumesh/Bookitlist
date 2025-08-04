package com.app.bookitlist.data.api.interceptor

import com.app.bookitlist.data.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Skip auth for login/register endpoints
        if (originalRequest.url.encodedPath.contains("auth/login") ||
            originalRequest.url.encodedPath.contains("auth/register")) {
            return chain.proceed(originalRequest)
        }
        
        val token = tokenManager.getAccessToken()
        
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .build()
        
        val response = chain.proceed(authenticatedRequest)
        
        // Handle token refresh on 401
        if (response.code == 401) {
            response.close()
            val newToken = refreshToken()
            if (newToken != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }
        
        return response
    }
    
    private fun refreshToken(): String? {
        // Implement token refresh logic
        return try {
            val refreshToken = tokenManager.getRefreshToken()
            // Call refresh API and get new token
            // This should be done synchronously
            null // Return new token
        } catch (e: Exception) {
            null
        }
    }
}