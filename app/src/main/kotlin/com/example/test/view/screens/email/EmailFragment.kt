package com.example.test.view.screens.email

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.example.test.R
import com.example.test.data.form.RequestOtpForm
import com.example.test.utils.Validator
import com.example.test.utils.then
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.exts.goNext
import com.example.test.view.screens.home.LoginFlowData
import com.example.test.view.screens.otp.OtpActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class EmailFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_login
    private val loginForm by lazy { RequestOtpForm() }
    override val viewModel: EmailViewModel by viewModel()
    private val loginFlowScope by lazy {
        getKoin().getOrCreateScope(
            "loginFlow",
            named("LoginFlowData")
        )
    }
    private val loginFlowData: LoginFlowData by loginFlowScope.inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxTextView
            .textChanges(edtEmail).debounce(2, TimeUnit.SECONDS)
            .then(viewModel, onNext = {
                btnLogin.isEnabled = Validator.isEmailPattern(edtEmail.text.toString())
            })
    }

    @OnClick(R.id.btnLogin)
    fun onClickLogin() {
        loginForm.email = edtEmail.text.toString()
        viewModel.requestOtp(loginForm)
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_OTP -> {
                loginFlowData.email = edtEmail.text.toString()
                goNext(OtpActivity::class.java)
            }
        }
    }
}