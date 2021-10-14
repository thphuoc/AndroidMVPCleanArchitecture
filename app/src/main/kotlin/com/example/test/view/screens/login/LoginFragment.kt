package com.example.test.view.screens.login

import com.example.test.R
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

    override fun navigate(viewNavigator: ViewNavigator) {

    }
}