package com.app.bookitlist.data.api

import android.content.Context
import com.app.bookitlist.data.api.interceptor.AuthInterceptor
import com.app.bookitlist.data.api.interceptor.LoggingInterceptor
import com.app.bookitlist.data.api.interceptor.NetworkInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    context: Context,
    authInterceptor: AuthInterceptor,
    networkInterceptor: NetworkInterceptor
) {

    companion object {
        private const val BASE_URL = "https://bookitlist.com/"
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 30L
        private const val WRITE_TIMEOUT = 30L
        private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
    }

    private val cache = Cache(File(context.cacheDir, "http_cache"), CACHE_SIZE)

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor(authInterceptor)
        .addInterceptor(LoggingInterceptor.create())
        .addNetworkInterceptor(networkInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}