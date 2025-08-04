package com.app.bookitlist.di

import android.content.Context
import com.app.bookitlist.data.api.ApiClient
import com.app.bookitlist.data.api.ApiService
import com.app.bookitlist.data.api.interceptor.AuthInterceptor
import com.app.bookitlist.data.api.interceptor.NetworkInterceptor
import com.app.bookitlist.data.respository.ApiRepository
import com.app.bookitlist.data.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
    
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }
    
    @Provides
    @Singleton
    fun provideApiClient(
        @ApplicationContext context: Context,
        authInterceptor: AuthInterceptor,
        networkInterceptor: NetworkInterceptor
    ): ApiClient {
        return ApiClient(context, authInterceptor, networkInterceptor)
    }
    
    @Provides
    @Singleton
    fun provideApiService(apiClient: ApiClient): ApiService {
        return apiClient.apiService
    }
    
    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository {
        return ApiRepository(apiService)
    }
}