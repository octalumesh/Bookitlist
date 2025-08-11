package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.request.RegisterRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.respository.ApiRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<AuthResponse>()
    val signUpResult: LiveData<AuthResponse> = _signUpResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signUp(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = apiRepository.register(request)
                when (response) {
                    is ApiResult.Success -> {
                        // Handle successful login
                        _signUpResult.postValue(response.data)
                    }

                    is ApiResult.Error -> {
                        // Handle error
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
}
