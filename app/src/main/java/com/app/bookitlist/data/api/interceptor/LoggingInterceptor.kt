package com.app.bookitlist.data.api.interceptor

import com.app.bookitlist.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class LoggingInterceptor {
    companion object {
        fun create(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor { message ->
                Timber.tag("API").d(message)
            }.apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        }
    }
}