package com.example.test.view.screens.login

import com.example.test.view.base.IView

interface ILoginView : IView {
    fun gotoHomeScreen()
    fun showLoginFailedMessage()
}