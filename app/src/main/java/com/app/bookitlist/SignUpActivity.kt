package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        phoneEditText = binding.phoneNumberEditText
        passwordEditText = binding.passwordEditText
        confirmPasswordEditText = binding.confirmPasswordEditText
        termsAndConditionsTextView = binding.termsAndConditionsTextView
        signUpButton = binding.signUpButton
        alreadyHaveAccountTextView = binding.alreadyHaveAccountTextView
        signInLinkTextView = binding.signInLinkTextView

        binding.signInLinkTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phoneNumber = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (fullName.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_enter_full_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_enter_username), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_enter_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_enter_phone_number), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_enter_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_msg_confirm_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, getString(R.string.err_msg_password_mismatch), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed with sign-up logic
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
