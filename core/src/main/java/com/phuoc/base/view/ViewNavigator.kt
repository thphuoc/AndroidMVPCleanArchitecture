package com.phuoc.base.view

import android.os.Bundle

data class ViewNavigator(
    val screen: ScreenEnum = ScreenEnum.NONE,
    val payload: Bundle = Bundle()
)