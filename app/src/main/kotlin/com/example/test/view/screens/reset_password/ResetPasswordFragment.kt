package com.example.test.view.screens.reset_password

import android.os.Bundle
import android.view.View
import android.widget.EditText
import butterknife.OnClick
import com.andreabaccega.formedittextvalidator.Validator
import com.example.test.R
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.base.ViewState
import com.example.test.view.exts.goBack
import com.example.test.view.exts.showIf
import com.phuoc.domain.form.ResetPasswordForm
import kotlinx.android.synthetic.main.fragment_forgot_password.tvError
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_reset_password
    override val viewModel: ResetPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val validator = object : Validator("Confirm password was not matched") {
            override fun isValid(et: EditText?): Boolean {
                return edtNewPassword.text.toString() == edtNewConfirmPassword.text.toString()
            }
        }
        edtNewConfirmPassword.addValidator(validator)
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

    override fun showError(viewState: ViewState<Any>) {
        tvError.showIf(showIf = true, false)
        tvError.text = viewState.errorMessage
    }
}