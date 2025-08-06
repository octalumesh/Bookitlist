package com.app.bookitlist

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.app.bookitlist.databinding.DialogOtpVerificationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpVerificationDialog(
    private val context: Context,
    private val phoneNumber: String,
    private val onVerificationSuccess: () -> Unit,
    private val onResendOtp: () -> Unit
) {

    private lateinit var dialog: Dialog
    private lateinit var binding: DialogOtpVerificationBinding
    private lateinit var otpEditTexts: List<EditText>
    private var currentOtp = ""

    fun show() {
        // Initialize ViewBinding
        binding = DialogOtpVerificationBinding.inflate(LayoutInflater.from(context))

        // Create dialog with ViewBinding root
        dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        // Setup all views and listeners
        setupViews()

        // Show dialog
        dialog.show()

        // Focus on first EditText and show keyboard
        otpEditTexts[0].requestFocus()
        showKeyboard(otpEditTexts[0])
    }

    private fun setupViews() {
        // Initialize OTP EditTexts using ViewBinding - much cleaner than findViewById
        otpEditTexts = listOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )

        // Setup OTP input behavior
        setupOtpInputs()

        // Close button click listener
        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Resend OTP click listener
        binding.tvResendOtp.setOnClickListener {
            clearOtpInputs()
            onResendOtp()
            Toast.makeText(context, "OTP resent successfully", Toast.LENGTH_SHORT).show()
        }

        // Verify button click listener
        binding.btnVerify.setOnClickListener {
            if (currentOtp.length == 6) {
                verifyOtp(currentOtp)
            } else {
                Toast.makeText(context, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOtpInputs() {
        otpEditTexts.forEachIndexed { index, editText ->
            // Add text change listener for each OTP input
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not needed for this implementation
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Not needed for this implementation
                }

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()

                    // If user entered a digit, move to next field
                    if (text.length == 1) {
                        if (index < otpEditTexts.size - 1) {
                            otpEditTexts[index + 1].requestFocus()
                        }
                    }

                    // Update current OTP string
                    updateCurrentOtp()
                }
            })

            // Handle backspace key press
            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (editText.text.isEmpty() && index > 0) {
                        // Move to previous field and clear it
                        otpEditTexts[index - 1].requestFocus()
                        otpEditTexts[index - 1].setText("")
                    }
                }
                false
            }
        }
    }

    private fun updateCurrentOtp() {
        // Combine all OTP digits into a single string
        currentOtp = otpEditTexts.joinToString("") { it.text.toString() }

        // Auto-verify when all 6 digits are entered
        if (currentOtp.length == 6) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(300) // Small delay for better user experience
                verifyOtp(currentOtp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        // Show loading state on verify button
        binding.btnVerify.isEnabled = false
        binding.btnVerify.text = "Verifying..."

        // Simulate OTP verification process
        // In real implementation, you would call your backend API here
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Simulate network call delay
                delay(1500)

                // For demo purposes, accept "123456" as valid OTP
                // Replace this with actual API call
                if (otp == "123456") {
                    Toast.makeText(context, "OTP verified successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    onVerificationSuccess()
                } else {
                    Toast.makeText(context, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show()
                    clearOtpInputs()
                    resetVerifyButton()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show()
                resetVerifyButton()
            }
        }
    }

    private fun resetVerifyButton() {
        binding.btnVerify.isEnabled = true
        binding.btnVerify.text = "Verify"
    }

    private fun clearOtpInputs() {
        // Clear all OTP input fields
        otpEditTexts.forEach { it.setText("") }
        // Focus back to first field
        otpEditTexts[0].requestFocus()
        currentOtp = ""
    }

    private fun showKeyboard(editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun dismiss() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}