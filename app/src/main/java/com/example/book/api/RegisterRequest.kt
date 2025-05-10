package com.example.book.api

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
) 