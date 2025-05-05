package com.example.book.model

data class Admin(
    val _id: String,
    val email: String,
    val password: String,
    val isVerified: Boolean,
    val verificationToken: String?
)
