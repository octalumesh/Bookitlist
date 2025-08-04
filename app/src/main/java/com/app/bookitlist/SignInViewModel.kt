package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookitlist.data.api.ApiService
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.models.response.User
import com.app.bookitlist.data.utils.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _signInResult = MutableLiveData<AuthResponse>()
    val signInResult: LiveData<AuthResponse> = _signInResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signIn(request: LoginRequest) {
        viewModelScope.launch {
            try {
                 val response = apiService.login(request) // Uncomment when ApiService is ready
                // _signInResult.postValue(response) // Uncomment when ApiService is ready
                // Mock response for now
                _signInResult.postValue(response.getResult())
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}