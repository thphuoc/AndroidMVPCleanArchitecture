package com.example.test.view.screens.contact

import com.example.test.data.dao.PersonDAO
import com.example.test.view.base.IView

interface IContactView : IView {
    fun showContact(contact: PersonDAO)
    fun backPreviousForUpdateSuccess(newContact: PersonDAO)
}