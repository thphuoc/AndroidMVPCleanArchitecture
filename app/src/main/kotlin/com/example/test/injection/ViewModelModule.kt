package com.example.test.injection

import com.example.test.data.usecases.RequestOtpUseCase
import com.example.test.data.usecases.VerifyOtpUseCase
import com.example.test.view.screens.email.EmailViewModel
import com.example.test.view.screens.home.HomeViewModel
import com.example.test.view.screens.otp.OtpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EmailViewModel(RequestOtpUseCase(get())) }
    viewModel { OtpViewModel(VerifyOtpUseCase(get())) }
    viewModel { HomeViewModel() }
}