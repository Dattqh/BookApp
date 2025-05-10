package com.example.book.di

import com.example.book.repository.AuthRepository
import com.example.book.viewmodel.LoginViewModel
import com.example.book.viewmodel.RegistrationViewModel

object AppModule {
    private val authRepository by lazy {
        AuthRepository()
    }

    fun provideRegistrationViewModel(): RegistrationViewModel {
        return RegistrationViewModel(authRepository)
    }

    fun provideLoginViewModel(): LoginViewModel {
        return LoginViewModel(authRepository)
    }
} 