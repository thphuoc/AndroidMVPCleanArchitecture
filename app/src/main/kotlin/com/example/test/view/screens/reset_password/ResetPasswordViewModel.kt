package com.example.test.view.screens.reset_password

import com.example.test.exts.observe
import com.example.test.view.base.*
import com.phuoc.domain.form.ResetPasswordForm
import com.phuoc.domain.usecases.IResetPasswordUseCase

class ResetPasswordViewModel(private val useCase: IResetPasswordUseCase) : BaseViewModel<Any>() {

    fun submit(email: ResetPasswordForm) {
        useCase.execute(email).observe(this, onCompleted = {
            showToast("Reset password Success!")
            navigate(ViewNavigator(screen = ScreenEnum.SRC_HOME))
        })
    }
}