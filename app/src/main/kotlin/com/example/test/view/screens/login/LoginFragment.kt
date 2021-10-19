package com.example.test.view.screens.login

import butterknife.OnClick
import com.example.test.R
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.base.ViewState
import com.example.test.view.exts.goNext
import com.example.test.view.exts.showIf
import com.example.test.view.screens.forgot_password.ForgotPasswordActivity
import com.example.test.view.screens.home.HomeActivity
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

    override fun showError(viewState: ViewState<Any>) {
        tvError.showIf(showIf = true, false)
        tvError.text = viewState.errorMessage
    }
}