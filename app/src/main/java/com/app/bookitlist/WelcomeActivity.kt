package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.bookitlist.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    // Declare a variable for the binding class
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        // Set the content view to the root of the binding
        setContentView(binding.root)

        // Set OnClickListener for the Sign In button
        binding.signInButton.setOnClickListener {
            // Create an Intent to start SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            // You typically don't finish WelcomeActivity here,
            // as the user might want to come back to choose Sign Up.
        }

        // Set OnClickListener for the Sign Up button
        binding.signUpButton.setOnClickListener {
            // Create an Intent to start SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            // Similarly, don't finish WelcomeActivity here.
        }
    }


}