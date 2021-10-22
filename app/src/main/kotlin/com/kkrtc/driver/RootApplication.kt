package com.kkrtc.driver

import androidx.multidex.MultiDexApplication
import com.kkrtc.driver.injection.apiServiceModule
import com.kkrtc.driver.injection.sharedDataModule
import com.kkrtc.driver.injection.useCaseModule
import com.kkrtc.driver.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RootApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RootApplication)
            modules(viewModelModule, sharedDataModule, apiServiceModule, useCaseModule)
        }
    }
}