package com.example.test.injection

import com.example.test.data.apimgr.APIService
import com.example.test.data.services.IOtpService
import com.example.test.data.usecases.VerifyOtpUseCase
import com.example.test.view.screens.email.EmailViewModel
import com.example.test.view.screens.home.HomeViewModel
import com.example.test.view.screens.otp.OtpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiServiceModule = module {
    factory { APIService.build(IOtpService::class.java) }
}