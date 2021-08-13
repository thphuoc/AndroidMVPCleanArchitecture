package com.example.test.view.screens.login

import com.example.test.data.form.LoginForm
import com.example.test.data.usecases.LoginUseCase
import com.example.test.exts.observe
import com.example.test.view.base.BasePresenter

class LoginPresenter(
    override val view: ILoginView,
    private val form: LoginForm
) : BasePresenter(view) {

    override fun init() {
        view.showContentView()
    }

    fun login() {
        LoginUseCase().execute(form).observe(view, onCompleted = {
            view.gotoHomeScreen()
        }, onError = {
            view.showLoginFailedMessage()
        })
    }
}