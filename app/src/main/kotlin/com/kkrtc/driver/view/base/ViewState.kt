package com.kkrtc.driver.view.base

data class ViewState<Data>(
    var viewState: ViewStateEnum = ViewStateEnum.PAGE_LOADING,
    var errorMessage: String = "",
    var errorTitle: String = "",
    val data: Data? = null
)