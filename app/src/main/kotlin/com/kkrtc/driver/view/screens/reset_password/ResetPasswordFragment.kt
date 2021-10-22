package com.kkrtc.driver.view.screens.reset_password

import android.os.Bundle
import android.view.View
import android.widget.EditText
import butterknife.OnClick
import com.andreabaccega.formedittextvalidator.Validator
import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.StateFragment
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.exts.goBack
import com.kkrtc.driver.view.exts.showIf
import com.phuoc.domain.form.ResetPasswordForm
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_reset_password
    override val viewModel: ResetPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(btnBack)
        val validator = object : Validator("Confirm password was not matched") {
            override fun isValid(et: EditText?): Boolean {
                return edtNewPassword.text.toString() == edtNewConfirmPassword.text.toString()
            }
        }
        edtNewConfirmPassword.addValidator(validator)
    }

    @OnClick(R.id.btnBack)
    fun onClickBack() {
        goBack()
    }

    @OnClick(R.id.btnSubmit)
    fun onClickSubmit() {

        val isValid = edtPassword.testValidity() and
                edtNewPassword.testValidity() and edtNewConfirmPassword.testValidity()
        if (!isValid) return
        tvError.showIf(showIf = false, true)
        viewModel.submit(
            ResetPasswordForm(
                currentPassword = edtPassword.text.toString(),
                newPassword = edtNewPassword.text.toString(),
                newConfirmPassword = edtNewConfirmPassword.text.toString()
            )
        )
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_HOME -> {
                goBack()
            }
        }
    }
}