package com.kkrtc.driver.view.screens.login

import butterknife.OnClick
import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.StateFragment
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.exts.goNext
import com.kkrtc.driver.view.exts.showIf
import com.kkrtc.driver.view.screens.forgot_password.ForgotPasswordActivity
import com.kkrtc.driver.view.screens.home.HomeActivity
import com.phuoc.domain.form.LoginForm
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_HOME -> {
                goNext(HomeActivity::class.java)
                activity?.finishAfterTransition()
            }
        }
    }

    @OnClick(R.id.btnLogin)
    fun onClickLogin() {
        val isValid = edtEmail.testValidity() and edtPassword.testValidity()
        if(!isValid) return
        tvError.showIf(showIf = false, true)
        viewModel.login(
            LoginForm(
                email = edtEmail.text.toString(),
                password = edtPassword.text.toString()
            )
        )
    }

    @OnClick(R.id.tvForgotPassword)
    fun onClickForgotPassword() {
        goNext(ForgotPasswordActivity::class.java)
    }
}