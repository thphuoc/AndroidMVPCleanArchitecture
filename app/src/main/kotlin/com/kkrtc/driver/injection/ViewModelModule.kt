package com.kkrtc.driver.injection

import com.kkrtc.driver.view.screens.forgot_password.ForgotPasswordViewModel
import com.kkrtc.driver.view.screens.home.HomeViewModel
import com.kkrtc.driver.view.screens.login.LoginViewModel
import com.kkrtc.driver.view.screens.reset_password.ResetPasswordViewModel
import com.kkrtc.driver.view.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { ResetPasswordViewModel(get()) }
}