package com.app.bookitlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.bookitlist.data.models.request.RegisterRequest
import com.app.bookitlist.data.utils.DialogFragmentProgressManager
import com.app.bookitlist.data.utils.hideKeyboard
import com.app.bookitlist.data.utils.setTransparentStatusBarAndEdgeToEdge
import com.app.bookitlist.databinding.SignupActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding

    private lateinit var logoImageView: ImageView
    private lateinit var signUpTitleTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var termsAndConditionsTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var signInLinkTextView: TextView

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setTransparentStatusBarAndEdgeToEdge()
        setContentView(binding.root)

        // Initialize views
        logoImageView = binding.logo
        signUpTitleTextView = binding.signUpTitle
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
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phoneNumber = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

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

            val registerRequest = RegisterRequest(
                task = "register",
                firstName = username,
                email = email,
                ref = "",
                phoneNumber = "+91$phoneNumber",
                password = password,
            )
            hideKeyboard()
            DialogFragmentProgressManager.showApiProgress(supportFragmentManager)
            signUpViewModel.signUp(registerRequest)
        }

        // Observe signInResult LiveData
        signUpViewModel.signUpResult.observe(this, Observer { authResponse ->
            // Handle successful sign-in
            // For example, show a toast and navigate to the next screen
            println("Sign-in successful: ${authResponse}")
            //Toast.makeText(this, "Sign-in successful: ${authResponse.user?.name}", Toast.LENGTH_LONG).show()
            // TODO: Navigate to your main activity or dashboard
            DialogFragmentProgressManager.dismissProgress()
        })

        // Observe error LiveData
        signUpViewModel.error.observe(this, Observer { errorMessage ->
            // Handle error
            // For example, show a toast with the error message
            //showDialog()
            Timber.e("Sign-in error: $errorMessage")
            DialogFragmentProgressManager.dismissProgress()
            Toast.makeText(this, "$errorMessage", Toast.LENGTH_LONG).show()
        })

        // Observe error LiveData
        signUpViewModel.token.observe(this, Observer { token ->
            // Handle error
            // For example, show a toast with the error message
            //showDialog()
            DialogFragmentProgressManager.dismissProgress()
            showDialog(token)
        })
    }

    fun showDialog(token: String) {
        val otpDialog = OtpVerificationDialog(
            token = token,
            phoneNumber = "+91${phoneEditText.text.toString().trim()}",
            onVerificationSuccess = { /* Handle success */ },
            onResendOtp = { /* Handle resend */ },
            activityContext = this
        )
        otpDialog.show(supportFragmentManager, "OtpVerificationDialog")
    }
}
