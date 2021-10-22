package com.kkrtc.driver.injection

import android.content.Context
import android.location.Location
import com.kkrtc.driver.view.screens.home.LoginFlowData
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedDataModule = module {
    single { LoginFlowData() }
    single { PublishSubject.create<Location>() }
    factory { androidContext().getSharedPreferences("session", Context.MODE_PRIVATE) }
}