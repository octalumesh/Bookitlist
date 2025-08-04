package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.databinding.SigninActivityBinding
import com.app.bookitlist.data.models.response.AuthResponse
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: SigninActivityBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe signInResult LiveData
        signInViewModel.signInResult.observe(this, Observer { authResponse ->
            // Handle successful sign-in
            // For example, show a toast and navigate to the next screen
            Toast.makeText(this, "Sign-in successful: ${authResponse.user?.name}", Toast.LENGTH_LONG).show()
            // TODO: Navigate to your main activity or dashboard
            showDialog()
        })

        // Observe error LiveData
        signInViewModel.error.observe(this, Observer { errorMessage ->
            // Handle error
            // For example, show a toast with the error message
            showDialog()
            Timber.e("Sign-in error: $errorMessage")
            Toast.makeText(this, "Sign-in error: $errorMessage", Toast.LENGTH_LONG).show()
        })

        // Example: Trigger sign-in when a button is clicked
         binding.signInButton.setOnClickListener {
             val phoneNumber = binding.phoneNumberEditText.text.toString()
             val password = binding.passwordEditText.text.toString()
             // TODO: Add proper validation
             val loginRequest = LoginRequest(phoneNumber, password)
             signInViewModel.signIn(loginRequest)
         }

        binding.signUpLinkTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun showDialog() {
        val otpDialog = OtpVerificationDialog(
            context = this,
            phoneNumber = "+1234567890",
            onVerificationSuccess = { /* Handle success */ },
            onResendOtp = { /* Handle resend */ }
        )
        otpDialog.show()
    }
}
