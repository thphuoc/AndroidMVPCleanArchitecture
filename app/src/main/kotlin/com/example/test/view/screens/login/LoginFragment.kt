package com.example.test.view.screens.login

import butterknife.OnClick
import com.example.test.R
import com.example.test.data.form.LoginForm
import com.example.test.view.base.StateFragment
import com.example.test.view.exts.goNext
import com.example.test.view.exts.showIf
import com.example.test.view.screens.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : StateFragment(), ILoginView {
    override val layoutResId: Int = R.layout.fragment_login
    private val loginForm by lazy { LoginForm() }
    override val presenter: LoginPresenter = LoginPresenter(this, loginForm)

    @OnClick(R.id.btnLogin)
    fun onClickLogin() {
        loginForm.email = edtEmail.text.toString()
        loginForm.password = edtPassword.text.toString()
        tvErrorMessage.showIf(showIf = false, goneIf = true)
        presenter.login()
    }

    override fun showLoginFailedMessage() {
        tvErrorMessage.showIf(showIf = true, goneIf = false)
    }

    override fun gotoHomeScreen() {
        goNext(HomeActivity::class.java)
    }
}