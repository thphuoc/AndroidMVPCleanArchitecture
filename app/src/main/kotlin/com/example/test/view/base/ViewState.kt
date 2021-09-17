package com.example.test.view.base

data class ViewState<Data>(
    var viewState: ViewStateEnum = ViewStateEnum.PAGE_LOADING,
    var errorMessage: String = "",
    val data: Data? = null
)