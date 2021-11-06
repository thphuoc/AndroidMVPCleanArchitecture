package com.example.test.view.screens.home

import android.os.Bundle
import android.view.View
import com.example.test.R
import com.phuoc.base.view.StateFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()
    private val loginFlowScope by lazy {
        getKoin().getOrCreateScope(
            "loginFlow",
            named("LoginFlowData")
        )
    }
    private val loginFlowData: LoginFlowData by loginFlowScope.inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDesc.text = getString(R.string.lb_welcome, loginFlowData.email)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginFlowScope.closed
    }
}