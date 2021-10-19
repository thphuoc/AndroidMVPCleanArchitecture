package com.example.test.view.screens.splash

import com.example.test.exts.observe
import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.ViewNavigator
import com.example.test.view.screens.home.LoginFlowData
import com.phuoc.data.apimgr.APIService
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
            APIService.setAccessToken(session?.token ?: "")
            loginFlowData.driverName = session?.name ?: ""
            Completable.fromCallable { Thread.sleep(1000) }.observe(this, showLoadingDialog = false,
                onCompleted = {
                    navigate(ViewNavigator(screen = ScreenEnum.SRC_HOME))
                })
        }
    }
}