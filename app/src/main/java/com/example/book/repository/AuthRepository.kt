package com.example.book.repository

import com.example.book.api.AuthApi
import com.example.book.api.RetrofitClient
import com.example.book.model.LoginRequest
import com.example.book.model.RegisterRequest
import com.example.book.model.UserResponse

class AuthRepository {
    private val authApi: AuthApi = RetrofitClient.create(AuthApi::class.java)

    suspend fun register(request: RegisterRequest): Result<UserResponse> {
        return try {
            val response = authApi.register(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(request: LoginRequest): Result<UserResponse> {
        return try {
            val response = authApi.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
