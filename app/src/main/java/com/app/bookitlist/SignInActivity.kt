package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.utils.DialogFragmentProgressManager
import com.app.bookitlist.data.utils.hideKeyboard
import com.app.bookitlist.data.utils.setTransparentStatusBarAndEdgeToEdge
import com.app.bookitlist.data.utils.showToast
import com.app.bookitlist.databinding.SigninActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: SigninActivityBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        setTransparentStatusBarAndEdgeToEdge()
        setContentView(binding.root)

        // Observe signInResult LiveData
        signInViewModel.signInResult.observe(this, Observer { authResponse ->
            DialogFragmentProgressManager.dismissProgress()
            // Handle successful sign-in
            // For example, show a toast and navigate to the next screen
            println("Sign-in successful: ${authResponse}")
            //Toast.makeText(this, "Sign-in successful: ${authResponse.user?.name}", Toast.LENGTH_LONG).show()
            // TODO: Navigate to your main activity or dashboard
            //showDialog()
        })

        // Observe error LiveData
        signInViewModel.error.observe(this, Observer { errorMessage ->
            // Handle error
            // For example, show a toast with the error message
            //showDialog()
            DialogFragmentProgressManager.dismissProgress()
            Timber.e("Sign-in error: $errorMessage")
            Toast.makeText(this, "$errorMessage", Toast.LENGTH_LONG).show()
        })

        // Observe error LiveData
        signInViewModel.token.observe(this, Observer { token ->
            // Handle error
            // For example, show a toast with the error message
            //showDialog()
            DialogFragmentProgressManager.dismissProgress()
            Toast.makeText(this, "login successfully", Toast.LENGTH_LONG).show()
        })

        // Example: Trigger sign-in when a button is clicked
        binding.signInButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (phoneNumber.isEmpty()) {
                showToast(getString(R.string.err_msg_enter_phone_number))
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                showToast(getString(R.string.err_msg_enter_password))
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(task = "login", phoneNumber = "+91$phoneNumber", password = password)
            hideKeyboard()
            DialogFragmentProgressManager.showApiProgress(supportFragmentManager)
            signInViewModel.signIn(loginRequest)
        }

        binding.signUpLinkTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
