package com.app.bookitlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.bookitlist.databinding.SigninActivityBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: SigninActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access views using binding
        // Example: binding.usernameEditText.text
        // Example: binding.signInButton.setOnClickListener { /* Handle sign in */ }
    }
}
