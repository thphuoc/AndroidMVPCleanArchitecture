package com.kkrtc.driver.view.screens.login

import com.kkrtc.driver.exts.observe
import com.kkrtc.driver.view.base.*
import com.kkrtc.driver.view.screens.home.LoginFlowData
import com.phuoc.domain.form.LoginForm
import com.phuoc.domain.usecases.ICacheLoginSessionUseCase
import com.phuoc.domain.usecases.ILoginUseCase

class LoginViewModel(
    private val loginUseCase: ILoginUseCase,
    private val cacheSessionUseCase: ICacheLoginSessionUseCase,
    private val loginFlowData: LoginFlowData
) : BaseViewModel<Any>() {

    fun login(form: LoginForm) {
        loginUseCase.execute(form).observe(this, onSuccess = {
            loginFlowData.driverName = it.name
            cacheSessionUseCase.execute(it)
            navigate(ViewNavigator(screen = ScreenEnum.SRC_HOME))
        }, onError = {
            publishState(
                ViewState(
                    viewState = ViewStateEnum.ERROR,
                    errorMessage = it.message ?: ""
                )
            )
        })
    }
}