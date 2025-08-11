package com.app.bookitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookitlist.data.models.base.ApiResult
import com.app.bookitlist.data.models.request.LoginRequest
import com.app.bookitlist.data.models.response.AuthResponse
import com.app.bookitlist.data.respository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val _signInResult = MutableLiveData<AuthResponse>()
    val signInResult: LiveData<AuthResponse> = _signInResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun signIn(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = apiRepository.login(request)
                when (response) {
                    is ApiResult.Success -> {
                        // Handle successful login
                        _signInResult.postValue(response.data)
                    }

                    is ApiResult.Error -> {
                        // Handle error
                        _error.postValue(response.exception.message ?: "Unknown error")
                    }
                    is ApiResult.Loading -> {
                        // Handle loading state if needed
                        // This can be used to show a loading spinner in the UI
                    }

                    is ApiResult.TokenSuccess -> {
                        _token.postValue(response.userToken)
                    }
                }

            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}