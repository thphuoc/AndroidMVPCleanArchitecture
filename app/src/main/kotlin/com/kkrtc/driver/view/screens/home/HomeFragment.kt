package com.kkrtc.driver.view.screens.home

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import butterknife.OnClick
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import com.kkrtc.driver.R
import com.kkrtc.driver.exts.observe
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.StateFragment
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.exts.goBack
import com.kkrtc.driver.view.exts.goNext
import com.kkrtc.driver.view.exts.showIf
import com.kkrtc.driver.view.exts.showMessageYesNoDialog
import com.kkrtc.driver.view.screens.login.LoginActivity
import com.kkrtc.driver.view.screens.reset_password.ResetPasswordActivity
import io.reactivex.subjects.PublishSubject
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
    private val locationSubject: PublishSubject<Location> by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(imgMenu)
        fitStatusBar(imgLogo)
        tvDriverName.text = loginFlowData.driverName

        placeHolderView.addView(viewBinder)

        checkPlayServicesAvailable()

        locationSubject.observe(viewModel, showLoadingDialog = false, onSuccess = {
            tvInfo.text = "Sharing location\nLat: ${it.latitude},\nLng: ${it.longitude}"
        })
    }


    private fun checkPlayServicesAvailable() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val status = apiAvailability.isGooglePlayServicesAvailable(requireContext())
        if (status != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(requireActivity(), status, 1)?.show()
            } else {
                Snackbar.make(
                    llRootView,
                    "Google Play Services unavailable. This app will not work",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }
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


    fun showPip() {
        appBar?.showIf(showIf = false, false)
        llRootView?.showIf(showIf = false, false)
        tvInfo?.showIf(showIf = true, false)
    }

    fun hidePip() {
        appBar?.showIf(showIf = true, false)
        llRootView?.showIf(showIf = true, false)
        tvInfo?.showIf(showIf = false, false)
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