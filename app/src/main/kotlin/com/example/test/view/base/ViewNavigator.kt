package com.example.test.view.base

import android.os.Bundle

data class ViewNavigator(
    val screen: ScreenEnum = ScreenEnum.NONE,
    val payload: Bundle = Bundle()
)