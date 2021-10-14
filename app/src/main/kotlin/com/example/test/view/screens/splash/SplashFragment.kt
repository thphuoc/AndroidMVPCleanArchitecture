package com.example.test.view.screens.splash

import com.example.test.R
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.exts.goNext
import com.example.test.view.screens.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun navigate(viewNavigator: ViewNavigator) {
        goNext(LoginActivity::class.java)
    }
}