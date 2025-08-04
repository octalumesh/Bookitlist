package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Assume ApiService and SignUpRequest/SignUpResponse exist
// import com.app.bookitlist.network.ApiService
// import com.app.bookitlist.models.SignUpRequest
// import com.app.bookitlist.models.SignUpResponse

class SignUpViewModel /*(private val apiService: ApiService)*/ : ViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResponse>()
    val signUpResult: LiveData<SignUpResponse> = _signUpResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                // val response = apiService.signUp(request) // Uncomment when ApiService is ready
                // _signUpResult.postValue(response) // Uncomment when ApiService is ready
                // Mock response for now
                 _signUpResult.postValue(SignUpResponse("User created successfully"))
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}

// Mock data classes for now - replace with your actual models
data class SignUpRequest(val fullName: String, val username: String, val email: String, val phone: String, val password: String)
data class SignUpResponse(val message: String)
