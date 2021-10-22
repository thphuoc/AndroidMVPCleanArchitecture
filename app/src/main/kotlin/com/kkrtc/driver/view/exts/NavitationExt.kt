package com.kkrtc.driver.view.exts

import android.content.Intent
import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.BaseActivity
import com.kkrtc.driver.view.base.StateFragment

fun StateFragment<*>.goBack() {
    activity?.finish()
}

fun <S : BaseActivity> StateFragment<*>.goBack(target: Class<S>) {
    activity?.run {
        val intent = Intent(this, target)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.left_in, R.anim.right_out)
        activity?.finishAfterTransition()
    }
}

fun <S : BaseActivity> StateFragment<*>.goNext(target: Class<S>) {
    activity?.run {
        val intent = Intent(this, target)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }
}