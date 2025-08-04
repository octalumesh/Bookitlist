package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.bookitlist.databinding.SignupActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding

    private lateinit var logoImageView: ImageView
    private lateinit var signUpTitleTextView: TextView
    private lateinit var fullNameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var termsAndConditionsTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var signInLinkTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        logoImageView = binding.logo
        signUpTitleTextView = binding.signUpTitle
        fullNameEditText = binding.fullNameEditText
        usernameEditText = binding.usernameEditText
        emailEditText = binding.emailEditText
        phoneEditText = binding.phoneEditText
        passwordEditText = binding.passwordEditText
        confirmPasswordEditText = binding.confirmPasswordEditText
        termsAndConditionsTextView = binding.termsAndConditionsTextView
        signUpButton = binding.signUpButton
        alreadyHaveAccountTextView = binding.alreadyHaveAccountTextView
        signInLinkTextView = binding.signInLinkTextView

        // You can set up listeners or other logic here
        binding.signInLinkTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
