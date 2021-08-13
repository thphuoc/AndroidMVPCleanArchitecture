package com.example.test.view.screens.home

import com.example.test.data.dao.PersonDAO
import com.example.test.data.dao.base.PagingDataDAO
import com.example.test.view.base.IView

interface IHomeView : IView {
    fun showContacts(data: PagingDataDAO<PersonDAO>)
}