package com.example.test

import androidx.multidex.MultiDexApplication
import com.example.test.injection.apiServiceModule
import com.example.test.injection.sharedDataModule
import com.example.test.injection.useCaseModule
import com.example.test.injection.viewModelModule
import com.phuoc.data.apimgr.APIService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RootApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        APIService.setLoggable(true)
        startKoin {
            androidContext(this@RootApplication)
            modules(viewModelModule, sharedDataModule, apiServiceModule, useCaseModule)
        }
    }
}