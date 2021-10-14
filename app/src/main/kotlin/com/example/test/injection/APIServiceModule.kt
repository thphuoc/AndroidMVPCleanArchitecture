package com.example.test.injection

import com.phuoc.data.apimgr.APIService
import com.phuoc.data.services.IOtpService
import org.koin.dsl.module

val apiServiceModule = module {
    factory { APIService.build(IOtpService::class.java) }
}