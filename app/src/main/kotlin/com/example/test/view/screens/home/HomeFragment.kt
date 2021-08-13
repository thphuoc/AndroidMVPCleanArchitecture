package com.example.test.view.screens.home

import com.example.test.data.dao.PersonDAO
import com.example.test.data.dao.base.PagingDataDAO
import com.example.test.view.base.TitleBarSearchFragment
import com.example.test.view.screens.home.viewBinder.ContactItemViewBinder

class HomeFragment : TitleBarSearchFragment(), IHomeView {
    override val presenter: HomePresenter = HomePresenter(this, searchForm)
    override fun showContacts(pageData: PagingDataDAO<PersonDAO>) {
        val viewBinders = pageData.data?.map { contactToDisplay ->
            ContactItemViewBinder(this, contactToDisplay)
        } ?: listOf()

        showListItems(
            viewBinders = viewBinders,
            appendList = searchForm.page > 0,
            hasMore = pageData.hasMore()
        )
    }
}