package com.example.test.view.screens.otp

import com.example.test.exts.observe
import com.phuoc.base.view.*
import com.phuoc.data.usecases.VerifyOtpUseCase

class OtpViewModel(private val useCase: VerifyOtpUseCase) : BaseViewModel<Any>() {

    fun verifyOtp(form: com.phuoc.domain.form.VerifyOtpForm) {
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