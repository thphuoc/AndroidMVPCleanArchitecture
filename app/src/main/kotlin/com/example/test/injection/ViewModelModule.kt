package com.example.test.injection

import com.example.test.view.screens.email.EmailViewModel
import com.example.test.view.screens.home.HomeViewModel
import com.example.test.view.screens.otp.OtpViewModel
import com.phuoc.data.usecases.RequestOtpUseCase
import com.phuoc.data.usecases.VerifyOtpUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EmailViewModel(RequestOtpUseCase(get())) }
    viewModel { OtpViewModel(VerifyOtpUseCase(get())) }
    viewModel { HomeViewModel() }
}