package com.example.book.model

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val token: String
) 