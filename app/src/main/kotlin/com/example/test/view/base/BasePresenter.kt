package com.example.test.view.base

open class BasePresenter(open val view: IView) : IPresenter {
    override fun init() {
        view.showLoadingView()
    }

    override fun loadData() {

    }
}