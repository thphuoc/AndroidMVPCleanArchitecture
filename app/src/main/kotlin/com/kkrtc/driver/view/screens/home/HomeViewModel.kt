package com.kkrtc.driver.view.screens.home

import com.kkrtc.driver.exts.observe
import com.kkrtc.driver.view.base.BaseViewModel
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.ViewNavigator
import com.phuoc.domain.usecases.IClearLoginSessionUseCase
import com.phuoc.domain.usecases.ILogoutUseCase

class HomeViewModel(
    private val logoutUseCase: ILogoutUseCase,
    private val clearCacheUseCase: IClearLoginSessionUseCase
) : BaseViewModel<Any>() {

    fun logout() {
        logoutUseCase.execute().observe(this, onCompleted = {
            clearCacheUseCase.execute()
            navigate(ViewNavigator(screen = ScreenEnum.SRC_LOGIN))
        })
    }
}