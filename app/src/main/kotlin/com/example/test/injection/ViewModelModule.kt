package com.example.test.injection

import com.example.test.view.screens.home.HomeViewModel
import com.example.test.view.screens.login.LoginViewModel
import com.example.test.view.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SplashViewModel() }
}