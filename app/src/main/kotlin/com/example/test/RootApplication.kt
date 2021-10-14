package com.example.test

import androidx.multidex.MultiDexApplication
import com.phuoc.data.apimgr.APIService
import com.example.test.injection.apiServiceModule
import com.example.test.injection.sharedDataModule
import com.example.test.injection.viewModelModule
import org.koin.core.context.startKoin


class RootApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        com.phuoc.data.apimgr.APIService.setLoggable(true)
        startKoin {
            modules(viewModelModule, sharedDataModule, apiServiceModule)
        }
    }
}