package com.example.test.injection

import android.content.Context
import com.example.test.view.screens.home.LoginFlowData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedDataModule = module {
    single { LoginFlowData() }
    factory { androidContext().getSharedPreferences("session", Context.MODE_PRIVATE) }
}