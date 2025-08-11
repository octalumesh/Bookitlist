package com.app.bookitlist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.bookitlist.data.models.request.OTPRequest
import com.app.bookitlist.data.utils.DialogFragmentProgressManager
import com.app.bookitlist.databinding.DialogOtpVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.getValue

@AndroidEntryPoint
class OtpVerificationDialog(
    private val activityContext: FragmentActivity, // Renamed to avoid confusion with Fragment.activity
    private val phoneNumber: String,
    private val token: String,
    private val onVerificationSuccess: () -> Unit,
    private val onResendOtp: () -> Unit
) : DialogFragment() {

    private var _binding: DialogOtpVerificationBinding? = null
    private val binding get() = _binding!! // Property delegate to ensure non-null access in lifecycle methods

    private lateinit var otpEditTexts: List<EditText>
    private var currentOtp = ""
    private val viewModel: OtpVerificationViewModel by lazy {
        ViewModelProvider(activityContext)[OtpVerificationViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogOtpVerificationBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Return the binding root if you are using onCreateDialog with a custom view.
        // DialogFragment will take care of adding it to the dialog.
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        // Focus on first EditText and show keyboard
        if (::otpEditTexts.isInitialized && otpEditTexts.isNotEmpty()) {
            otpEditTexts[0].requestFocus()
            showKeyboard(otpEditTexts[0])
        }
    }


    private fun setupViews() {
        // Initialize OTP EditTexts using ViewBinding
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
            dismiss() // Use DialogFragment's dismiss method
        }

        // Resend OTP click listener
        binding.tvResendOtp.setOnClickListener {
            clearOtpInputs()
            onResendOtp()
            viewModel.resendOtp()
            Toast.makeText(activityContext, "OTP resent successfully", Toast.LENGTH_SHORT).show()
        }

        // Verify button click listener
        binding.btnVerify.setOnClickListener {
            if (currentOtp.length == 6) {
                DialogFragmentProgressManager.showApiProgress(activityContext.supportFragmentManager)
                viewModel.verifyOtp(OTPRequest(otp = currentOtp, token = token));
            } else {
                Toast.makeText(activityContext, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.verificationStatus.observe(viewLifecycleOwner, Observer { authResponse ->
            DialogFragmentProgressManager.dismissProgress()
            println("Sign-in successful: ${authResponse}")
            Toast.makeText(activityContext, "Sign-in successful", Toast.LENGTH_LONG).show() // Simplified toast
            onVerificationSuccess() // Call the success callback
            dismiss() // Dismiss dialog on success
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            DialogFragmentProgressManager.dismissProgress()
            Toast.makeText(activityContext, "$errorMessage", Toast.LENGTH_LONG).show()
        })

        viewModel.token.observe(viewLifecycleOwner, Observer { newToken ->
             DialogFragmentProgressManager.dismissProgress()
             // Assuming token observation means a new token was received after resend,
             // No specific UI action mentioned here other than dismissing progress.
             // If this observer is for the initial token, it might need different handling.
             // For now, let's assume it's related to OTP flow updates.
            Timber.d("Token observed: $newToken")
            onVerificationSuccess() // Call the success callback
            dismiss() // Dismiss dialog on token observation
        })
    }

    private fun setupOtpInputs() {
        otpEditTexts.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    if (text.length == 1 && index < otpEditTexts.size - 1) {
                        otpEditTexts[index + 1].requestFocus()
                    }
                    updateCurrentOtp()
                }
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (editText.text.isEmpty() && index > 0) {
                        otpEditTexts[index - 1].requestFocus()
                        otpEditTexts[index - 1].setText("")
                        // Update current OTP after clearing a field via backspace
                        updateCurrentOtp()
                    } else if (editText.text.isNotEmpty() && index > 0) {
                        // If current field has text, backspace should clear current field first.
                        // Default behavior handles this for the most part.
                        // If it becomes empty, then we might want to move focus.
                        // The current logic moves focus if it's already empty.
                    } else if (editText.text.isEmpty() && index == 0) {
                        // If first field is empty and backspace is pressed, do nothing extra.
                    }
                }
                false
            }
        }
    }

    private fun updateCurrentOtp() {
        currentOtp = otpEditTexts.joinToString("") { it.text.toString() }
        resetVerifyButton() // Removed auto-verify, button click is explicit
    }

    private fun resetVerifyButton() {
        binding.btnVerify.isEnabled = true // Enable only if OTP is full
        binding.btnVerify.text = "Verify"
    }

    private fun clearOtpInputs() {
        otpEditTexts.forEach { it.setText("") }
        if (otpEditTexts.isNotEmpty()) {
            otpEditTexts[0].requestFocus()
        }
        currentOtp = ""
        resetVerifyButton() // Update button state after clearing
    }

    private fun showKeyboard(editText: EditText) {
        val imm = activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear view binding reference
    }

    // To show this dialog, you would call:
    // val dialog = OtpVerificationDialog(requireActivity(), "phoneNumber", "token", onSuccessLambda, onResendLambda)
    // dialog.show(requireActivity().supportFragmentManager, "OtpVerificationDialogTag")
}
