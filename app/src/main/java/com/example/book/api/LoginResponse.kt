package com.example.book.api

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val fullName: String,
    val email: String
) 