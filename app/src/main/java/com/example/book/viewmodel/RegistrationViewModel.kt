package com.example.book.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.RegisterRequest
import com.example.book.model.UserResponse
import com.example.book.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class RegistrationViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()

    fun register(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                val request = RegisterRequest(fullName, email, password)
                val result = authRepository.register(request)
                result.fold(
                    onSuccess = { userResponse ->
                        _registrationState.value = RegistrationState.Success("Đăng ký thành công")
                    },
                    onFailure = { error ->
                        Log.e("RegistrationViewModel", "Registration failed", error)
                        val message = when (error) {
                            is IOException -> "Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng."
                            else -> error.message ?: "Đăng ký thất bại"
                        }
                        _registrationState.value = RegistrationState.Error(message)
                    }
                )
            } catch (e: Exception) {
                Log.e("RegistrationViewModel", "Unexpected error", e)
                val message = when (e) {
                    is IOException -> "Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng."
                    else -> e.message ?: "Lỗi không xác định xảy ra."
                }
                _registrationState.value = RegistrationState.Error(message)
            }
        }
    }
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    data class Success(val message: String) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
} 