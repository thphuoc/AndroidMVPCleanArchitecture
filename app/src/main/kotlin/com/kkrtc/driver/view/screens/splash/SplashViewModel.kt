package com.kkrtc.driver.view.screens.splash

import com.kkrtc.driver.exts.observe
import com.kkrtc.driver.view.base.BaseViewModel
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.screens.home.LoginFlowData
import com.phuoc.domain.usecases.IGetCacheSessionUseCase
import io.reactivex.Completable

class SplashViewModel(
    private val getCacheSessionUseCase: IGetCacheSessionUseCase,
    private val loginFlowData: LoginFlowData
) : BaseViewModel<Any>() {
    override fun init() {
        val session = getCacheSessionUseCase.execute()
        if (session?.token.isNullOrBlank()) {
            Completable.fromCallable { Thread.sleep(1000) }.observe(this, showLoadingDialog = false,
                onCompleted = {
                    navigate(ViewNavigator(screen = ScreenEnum.SRC_LOGIN))
                })
        } else {
            loginFlowData.driverName = session?.name ?: ""
            Completable.fromCallable { Thread.sleep(1000) }.observe(this, showLoadingDialog = false,
                onCompleted = {
                    navigate(ViewNavigator(screen = ScreenEnum.SRC_HOME))
                })
        }
    }
}