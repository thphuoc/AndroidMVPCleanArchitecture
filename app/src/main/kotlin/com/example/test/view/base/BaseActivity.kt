package com.example.test.view.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.phuoc.domain.exceptions.InvalidAccessTokenException
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseActivity : AppCompatActivity() {
    private val layoutId: Int = R.layout.activity_base
    abstract val rootFragment : StateFragment<*>

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        RxJavaPlugins.setErrorHandler { throwable ->
            throwable.printStackTrace()
            var cause: Throwable? = null
            if (throwable is CompositeException) {
                cause = throwable.exceptions[0]
            }
            if (throwable is UndeliverableException) {
                cause = throwable.cause
            }
            cause?.let { errorCause ->
                if (errorCause is InvalidAccessTokenException) {
                    //Should be logout when token invalid
                }
            }
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        val view = LayoutInflater.from(this).inflate(layoutId, null, false)
        setContentView(view)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, rootFragment)
            .commitAllowingStateLoss()


    }

    override fun onBackPressed() {
        rootFragment.backPrevious()
    }
}

