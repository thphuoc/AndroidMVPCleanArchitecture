package com.example.test.view.screens.splash

import com.example.test.exts.observe
import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.ViewNavigator
import io.reactivex.Completable

class SplashViewModel : BaseViewModel<Any>() {
    override fun init() {
        Completable.fromCallable { Thread.sleep(1000) }.observe(this, showLoadingDialog = false,
            onCompleted = {
                navigate(ViewNavigator(screen = ScreenEnum.SRC_LOGIN))
            })
    }
}