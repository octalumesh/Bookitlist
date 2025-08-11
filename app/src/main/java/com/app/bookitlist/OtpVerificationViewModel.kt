package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.request.OTPRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.respository.ApiRepository
import com.app.bookitlist.data.utils.DialogFragmentProgressManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val _verificationStatus = MutableLiveData<AuthResponse>()
    val verificationStatus: LiveData<AuthResponse> = _verificationStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun verifyOtp(request: OTPRequest) {
        viewModelScope.launch {
            try {
                val response = apiRepository.otpVerification(request)
                when (response) {
                    is ApiResult.Success -> {
                        // Handle successful login
                        DialogFragmentProgressManager.dismissProgress()
                        _verificationStatus.postValue(response.data)
                    }

                    is ApiResult.TokenSuccess -> {
                        // Handle token success
                        DialogFragmentProgressManager.dismissProgress()
                        _token.postValue(response.userToken)
                    }

                    is ApiResult.Error -> {
                        // Handle error
                        DialogFragmentProgressManager.dismissProgress()
                        _error.postValue(response.exception.message ?: "Unknown error")
                    }

                    is ApiResult.Loading -> {
                        // Handle loading state if needed
                        // This can be used to show a loading spinner in the UI
                    }
                }

            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun resendOtp() {
        // TODO: Implement OTP resend logic here
    }
}
