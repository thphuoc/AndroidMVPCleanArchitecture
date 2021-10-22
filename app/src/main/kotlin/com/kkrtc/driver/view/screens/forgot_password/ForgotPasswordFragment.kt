package com.kkrtc.driver.view.screens.forgot_password

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.StateFragment
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.exts.goBack
import com.kkrtc.driver.view.exts.showIf
import com.phuoc.domain.form.ForgotPasswordForm
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_forgot_password
    override val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(btnBack)
    }

    @OnClick(R.id.btnBack)
    fun onClickBack() {
        goBack()
    }

    @OnClick(R.id.btnSubmit)
    fun onClickSubmit() {
        val isValid = edtEmail.testValidity()
        if(!isValid) return
        tvError.showIf(showIf = false, true)
        viewModel.submit(ForgotPasswordForm(email = edtEmail.text.toString()))
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_LOGIN -> goBack()
        }
    }
}