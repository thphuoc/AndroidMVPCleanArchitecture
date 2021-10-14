package com.example.test.view.screens.otp

import butterknife.OnClick
import butterknife.OnTextChanged
import com.example.test.R
import com.phuoc.domain.form.VerifyOtpForm
import com.phuoc.domain.utils.Validator
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.exts.goNext
import com.example.test.view.screens.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_otp.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_otp
    private val otpForm by lazy { com.phuoc.domain.form.VerifyOtpForm() }
    override val viewModel: OtpViewModel by viewModel()

    @OnTextChanged(R.id.edtOtp)
    fun onTextChanged() {
        btnSubmit.isEnabled = com.phuoc.domain.utils.Validator.isOtpPattern(edtOtp.text.toString())
    }

    @OnClick(R.id.btnSubmit)
    fun onClickLogin() {
        otpForm.otp = edtOtp.text.toString()
        viewModel.verifyOtp(otpForm)
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_HOME -> goNext(HomeActivity::class.java)
        }
    }

}