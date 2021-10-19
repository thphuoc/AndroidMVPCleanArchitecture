package com.example.test.injection

import com.example.test.view.screens.forgot_password.ForgotPasswordViewModel
import com.example.test.view.screens.home.HomeViewModel
import com.example.test.view.screens.login.LoginViewModel
import com.example.test.view.screens.reset_password.ResetPasswordViewModel
import com.example.test.view.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { ResetPasswordViewModel(get()) }
}