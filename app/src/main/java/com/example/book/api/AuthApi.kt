package com.example.book.api

import com.example.book.model.LoginRequest
import com.example.book.model.RegisterRequest
import com.example.book.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): UserResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): UserResponse
}
