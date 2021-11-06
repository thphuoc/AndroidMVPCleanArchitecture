package com.phuoc.base.exts

import android.content.Intent
import com.phuoc.base.view.BaseActivity
import com.phuoc.base.view.StateFragment

fun StateFragment<*>.goBack() {
    activity?.finish()
}

fun <S : BaseActivity> StateFragment<*>.goNext(target: Class<S>) {
    activity?.run {
        val intent = Intent(this, target)
        startActivity(intent)
    }
}