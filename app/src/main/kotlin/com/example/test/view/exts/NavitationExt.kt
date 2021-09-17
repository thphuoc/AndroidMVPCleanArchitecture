package com.example.test.view.exts

import android.content.Intent
import com.example.test.view.base.BaseActivity
import com.example.test.view.base.StateFragment

fun StateFragment<*>.goBack() {
    activity?.finish()
}

fun <S : BaseActivity> StateFragment<*>.goNext(target: Class<S>) {
    activity?.run {
        val intent = Intent(this, target)
        startActivity(intent)
    }
}