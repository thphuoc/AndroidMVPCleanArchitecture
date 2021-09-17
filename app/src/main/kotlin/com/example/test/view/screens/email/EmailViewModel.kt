package com.example.test.view.screens.email

import com.example.test.data.form.RequestOtpForm
import com.example.test.data.usecases.RequestOtpUseCase
import com.example.test.exts.observe
import com.example.test.view.base.*

open class EmailViewModel(private val useCase: RequestOtpUseCase) : BaseViewModel<Any>() {

    fun requestOtp(form: RequestOtpForm) {
        useCase.execute(form).observe(this, onCompleted = {
            navigate(ViewNavigator(screen = ScreenEnum.SRC_OTP))
        }, onError = {
            publishState(
                ViewState(
                    viewState = ViewStateEnum.ERROR
                )
            )
        })
    }
}