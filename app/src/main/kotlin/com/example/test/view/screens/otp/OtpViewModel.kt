package com.example.test.view.screens.otp

import com.example.test.data.form.VerifyOtpForm
import com.example.test.data.usecases.VerifyOtpUseCase
import com.example.test.exts.observe
import com.example.test.view.base.*

class OtpViewModel(private val useCase: VerifyOtpUseCase) : BaseViewModel<Any>() {

    fun verifyOtp(form: VerifyOtpForm) {
        useCase.execute(form).observe(this, onCompleted = {
            navigate(ViewNavigator(screen = ScreenEnum.SRC_HOME))
        }, onError = {
            state.viewState = ViewStateEnum.ERROR
            publishState(
                ViewState(
                    errorMessage = it.message ?: "",
                    viewState = ViewStateEnum.ERROR
                )
            )
        })
    }
}