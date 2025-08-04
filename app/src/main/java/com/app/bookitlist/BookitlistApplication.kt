package com.app.bookitlist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookitlistApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}