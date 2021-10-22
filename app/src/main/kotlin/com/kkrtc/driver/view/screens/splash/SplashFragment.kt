package com.kkrtc.driver.view.screens.splash

import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.StateFragment
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.exts.goNext
import com.kkrtc.driver.view.screens.home.HomeActivity
import com.kkrtc.driver.view.screens.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun navigate(viewNavigator: ViewNavigator) {
        when(viewNavigator.screen) {
            ScreenEnum.SRC_LOGIN -> {
                goNext(LoginActivity::class.java)
            }
            ScreenEnum.SRC_HOME -> {
                goNext(HomeActivity::class.java)
            }
        }

        activity?.finishAfterTransition()
    }
}