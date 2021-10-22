package com.kkrtc.driver.injection

import com.phuoc.data.apimgr.APIService
import com.phuoc.data.services.IAuthService
import com.phuoc.data.services.IGetVehicleService
import com.phuoc.data.services.ITrackingService
import org.koin.dsl.module

val apiServiceModule = module {
    factory {
        val serviceBuilder = APIService(get())
        serviceBuilder.setLoggable(com.kkrtc.driver.BuildConfig.DEBUG)
        serviceBuilder
    }
    factory { get<APIService>().build(IAuthService::class.java) }
    factory { get<APIService>().build(IGetVehicleService::class.java) }
    factory { get<APIService>().build(ITrackingService::class.java) }
}