package com.example.test.injection

import com.example.test.view.screens.home.LoginFlowData
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedDataModule = module {
    scope(named("LoginFlowData")) { scoped { LoginFlowData() } }
}