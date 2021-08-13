package com.example.test.view.screens.home

import com.example.test.data.form.SearchForm
import com.example.test.data.usecases.GetContactUseCase
import com.example.test.exts.observe
import com.example.test.view.base.BasePresenter

class HomePresenter(override val view: IHomeView, private val form: SearchForm) :
    BasePresenter(view) {
    override fun init() {
        super.init()
        loadData()
    }

    override fun loadData() {
        GetContactUseCase().execute(form)
            .observe(view, false, onSuccess = {
                view.showContacts(it)
            })
    }
}