package com.example.book.model

data class LoginResponse(
    val message: String,
    val admin: AdminInfo
)

data class AdminInfo(
    val id: String,
    val email: String,
    val role: String
) 