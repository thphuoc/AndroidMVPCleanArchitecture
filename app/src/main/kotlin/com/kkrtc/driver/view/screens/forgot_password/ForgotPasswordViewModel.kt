package com.kkrtc.driver.view.screens.forgot_password

import com.kkrtc.driver.exts.observe
import com.kkrtc.driver.view.base.*
import com.phuoc.domain.form.ForgotPasswordForm
import com.phuoc.domain.usecases.IForgotPasswordUseCase

class ForgotPasswordViewModel(private val useCase: IForgotPasswordUseCase) : BaseViewModel<Any>() {

    fun submit(email: ForgotPasswordForm) {
        useCase.execute(email).observe(this, onCompleted = {
            showToast("Submit success")
            navigate(ViewNavigator(screen = ScreenEnum.SRC_LOGIN))
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