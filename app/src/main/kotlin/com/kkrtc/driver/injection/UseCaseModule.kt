package com.kkrtc.driver.injection

import com.phuoc.data.usecases.*
import com.phuoc.domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    factory<ILoginUseCase> { LoginUseCase(get()) }
    factory<IForgotPasswordUseCase> { ForgotPasswordUseCase(get()) }
    factory<IResetPasswordUseCase> { ResetPasswordUseCase(get()) }
    factory<ILogoutUseCase> { LogoutUseCase(get()) }
    factory<ICacheLoginSessionUseCase> { CacheLoginSessionUseCase(get()) }
    factory<IGetCacheSessionUseCase> { GetCacheSessionUseCase(get()) }
    factory<IClearLoginSessionUseCase> { ClearLoginSessionUseCase(get()) }
    factory<IGetVehicleUseCase> { GetVehicleUseCase(get()) }
    factory<IGetSchedulersUseCase> { GetSchedulersUseCase(get()) }
    factory<IStartTrackingUseCase> { StartTrackingUseCase(get()) }
    factory<IStopTrackingUseCase> { StopTrackingUseCase(get()) }
    factory<IUpdateTrackingUseCase> { UpdateTrackingUseCase(get()) }
}