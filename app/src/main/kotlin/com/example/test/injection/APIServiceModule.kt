package com.example.test.injection

import com.phuoc.data.apimgr.APIService
import com.phuoc.data.services.IAuthService
import com.phuoc.data.services.IGetVehicleService
import com.phuoc.data.services.ITrackingService
import org.koin.dsl.module

val apiServiceModule = module {
    factory { APIService.build(IAuthService::class.java) }
    factory { APIService.build(IGetVehicleService::class.java) }
    factory { APIService.build(ITrackingService::class.java) }
}