package com.example.book.di

import com.example.book.repository.AuthRepository
import com.example.book.viewmodel.LoginViewModel
import com.example.book.viewmodel.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthRepository() }
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
} 