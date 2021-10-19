package com.example.test.view.screens.forgot_password

import butterknife.OnClick
import com.example.test.R
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.base.ViewState
import com.example.test.view.exts.goBack
import com.example.test.view.exts.showIf
import com.phuoc.domain.form.ForgotPasswordForm
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_forgot_password
    override val viewModel: ForgotPasswordViewModel by viewModel()

    @OnClick(R.id.btnSubmit)
    fun onClickSubmit() {
        tvError.showIf(showIf = false, true)
        viewModel.submit(ForgotPasswordForm(email = edtEmail.text.toString()))
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_LOGIN -> goBack()
        }
    }

    override fun showError(viewState: ViewState<Any>) {
        tvError.showIf(showIf = true, false)
        tvError.text = viewState.errorMessage
    }
}