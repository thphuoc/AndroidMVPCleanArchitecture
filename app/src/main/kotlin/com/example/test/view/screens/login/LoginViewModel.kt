package com.example.test.view.screens.login

import com.example.test.exts.observe
import com.example.test.view.base.*
import com.example.test.view.screens.home.LoginFlowData
import com.phuoc.data.apimgr.APIService
import com.phuoc.domain.form.LoginForm
import com.phuoc.domain.usecases.ICacheLoginSessionUseCase
import com.phuoc.domain.usecases.ILoginUseCase

class LoginViewModel(private val loginUseCase: ILoginUseCase,
                     private val cacheSessionUseCase : ICacheLoginSessionUseCase,
                     private val loginFlowData: LoginFlowData) : BaseViewModel<Any>() {

    fun login(form: LoginForm) {
        loginUseCase.execute(form).observe(this, onSuccess = {
            APIService.setAccessToken(it.token)
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