package com.example.test.view.screens.email

import com.example.test.exts.observe
import com.phuoc.base.view.*
import com.phuoc.domain.usecases.RequestOtpInterface

open class EmailViewModel(private val useCase: RequestOtpInterface) : BaseViewModel<Any>() {

    fun requestOtp(form: com.phuoc.domain.form.RequestOtpForm) {
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