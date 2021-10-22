package com.kkrtc.driver.view.screens.home

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.util.Rational
import android.view.View
import com.kkrtc.driver.view.base.BaseActivity

class HomeActivity : BaseActivity() {
    override val rootFragment = HomeFragment()

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = PictureInPictureParams.Builder()
            builder.setAspectRatio(Rational(1000, 700))
            val params = builder.build()
            enterPictureInPictureMode(params)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustFullScreen(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            adjustFullScreen(resources.configuration)
        }
    }

    fun adjustFullScreen(config: Configuration) {
        val decorView = window.decorView
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            rootFragment.showPip()
        } else {
            rootFragment.hidePip()
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }
}