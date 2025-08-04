package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {


    companion object {
        private const val SPLASH_DELAY: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Use a Handler to delay the start of WelcomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start WelcomeActivity
            val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
            startActivity(intent)

            // Finish SplashActivity so it's removed from the back stack
            finish()
        }, SPLASH_DELAY)
    }
}