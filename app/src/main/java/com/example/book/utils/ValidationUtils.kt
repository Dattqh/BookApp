package com.example.book.utils

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        // Password must be at least 8 characters long and contain at least:
        // - One uppercase letter
        // - One lowercase letter
        // - One number
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}\$"
        return password.matches(passwordRegex.toRegex())
    }

    fun getPasswordRequirements(): List<String> {
        return listOf(
            "Mật khẩu phải có ít nhất 8 ký tự",
            "Phải chứa ít nhất một chữ hoa",
            "Phải chứa ít nhất một chữ thường",
            "Phải chứa ít nhất một số"
        )
    }

    fun validateFullName(fullName: String): Boolean {
        return fullName.trim().length >= 2
    }

    fun getEmailError(email: String): String? {
        return when {
            email.isEmpty() -> "Email không được để trống"
            !isValidEmail(email) -> "Email không hợp lệ"
            else -> null
        }
    }

    fun getPasswordError(password: String): String? {
        return when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            !isValidPassword(password) -> "Mật khẩu không đáp ứng yêu cầu"
            else -> null
        }
    }

    fun getFullNameError(fullName: String): String? {
        return when {
            fullName.isEmpty() -> "Họ tên không được để trống"
            !validateFullName(fullName) -> "Họ tên phải có ít nhất 2 ký tự"
            else -> null
        }
    }
} 