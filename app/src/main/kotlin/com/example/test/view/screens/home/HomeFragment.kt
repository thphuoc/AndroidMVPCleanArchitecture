package com.example.test.view.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import butterknife.OnClick
import com.example.test.R
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.StateFragment
import com.example.test.view.base.ViewNavigator
import com.example.test.view.exts.goBack
import com.example.test.view.exts.goNext
import com.example.test.view.exts.showMessageYesNoDialog
import com.example.test.view.screens.locationService.TrackingService
import com.example.test.view.screens.login.LoginActivity
import com.example.test.view.screens.reset_password.ResetPasswordActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_home_content.*
import kotlinx.android.synthetic.main.view_home_navigation.*
import kotlinx.android.synthetic.main.view_home_title_bar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    private val loginFlowData: LoginFlowData by inject()
    private val viewBinder by lazy { DashboardViewBinder(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(imgMenu)
        fitStatusBar(imgLogo)
        tvDriverName.text = loginFlowData.driverName

        placeHolderView.addView(viewBinder)
    }

    @OnClick(R.id.imgMenu)
    fun onClickMenu() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    @OnClick(R.id.llLogout)
    fun onClickLogout() {
        showMessageYesNoDialog("Logout", "Do you want to logout?", onYes = {
            viewBinder.stopTracking()
            viewModel.logout()
        })
    }

    @OnClick(R.id.llChangePassword)
    fun onClickChangePassword() {
        goNext(ResetPasswordActivity::class.java)
    }

    override fun navigate(viewNavigator: ViewNavigator) {
        when (viewNavigator.screen) {
            ScreenEnum.SRC_LOGIN -> {
                goBack(LoginActivity::class.java)
                activity?.finishAfterTransition()
            }
        }
    }
}