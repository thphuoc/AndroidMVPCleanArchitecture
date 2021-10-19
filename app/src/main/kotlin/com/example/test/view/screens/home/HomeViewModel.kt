package com.example.test.view.screens.home

import com.example.test.exts.observe
import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.ViewNavigator
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