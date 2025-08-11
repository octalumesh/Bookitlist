package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpVerificationViewModel : ViewModel() {

    private val _verificationStatus = MutableLiveData<VerificationStatus>()
    val verificationStatus: LiveData<VerificationStatus> = _verificationStatus

    fun verifyOtp(otp: String) {
        _verificationStatus.value = VerificationStatus.Loading
        viewModelScope.launch {
            try {
                // Simulate network call delay
                delay(1500)
                if (otp == "123456") {
                    _verificationStatus.value = VerificationStatus.Success
                } else {
                    _verificationStatus.value = VerificationStatus.Error("Invalid OTP. Please try again.")
                }
            } catch (e: Exception) {
                _verificationStatus.value = VerificationStatus.Error("Verification failed. Please try again.")
            }
        }
    }

    fun resendOtp() {
        // TODO: Implement OTP resend logic here
    }
}

sealed class VerificationStatus {
    object Loading : VerificationStatus()
    object Success : VerificationStatus()
    data class Error(val message: String) : VerificationStatus()
}
