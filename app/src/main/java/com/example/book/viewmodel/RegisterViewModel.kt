package com.example.book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.RegisterRequest
import com.example.book.model.UserResponse
import com.example.book.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableStateFlow<Result<UserResponse>?>(null)
    val registerResult: StateFlow<Result<UserResponse>?> = _registerResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun register(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val request = RegisterRequest(fullName, email, password)
                val result = authRepository.register(request)
                _registerResult.value = result
            } catch (e: Exception) {
                _registerResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
} 