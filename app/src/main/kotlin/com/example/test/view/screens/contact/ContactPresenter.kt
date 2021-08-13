package com.example.test.view.screens.contact

import com.example.test.data.dao.PersonDAO
import com.example.test.data.form.ContactForm
import com.example.test.data.usecases.UpdateContactUseCase
import com.example.test.exts.observe
import com.example.test.view.base.BasePresenter

class ContactPresenter(override val view: IContactView) : BasePresenter(view) {

    fun updateContact(contactToUpdate: ContactForm, currentContact: PersonDAO) {
        //fake an update instead of call API
        currentContact.first_name = contactToUpdate.firstName
        currentContact.last_name = contactToUpdate.lasName
        currentContact.email = contactToUpdate.email

        UpdateContactUseCase().execute(contactToUpdate).observe(view, true, onCompleted = {
            view.backPreviousForUpdateSuccess(currentContact)
        })
    }
}