package com.example.test.view.screens.contact

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import butterknife.OnClick
import com.example.test.R
import com.example.test.data.dao.PersonDAO
import com.example.test.data.form.ContactForm
import com.example.test.view.base.TitleBarFragment
import com.example.test.view.exts.goBack
import com.example.test.view.exts.loadImage
import kotlinx.android.synthetic.main.view_contact_detail.*

class ContactFragment : TitleBarFragment(), IContactView {
    override val containerLayoutResId: Int = R.layout.view_contact_detail
    override val presenter: ContactPresenter = ContactPresenter(this)
    private val contactForm = ContactForm()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContactToDisplay?.apply { showContact(this) }
        populateContactForm()
        bindFormData()
    }

    private fun populateContactForm() {
        contactForm.firstName = mContactToDisplay?.first_name ?: ""
        contactForm.lasName = mContactToDisplay?.last_name ?: ""
        contactForm.email = mContactToDisplay?.email ?: ""
    }

    private fun bindFormData() {
        edtFirstName.addTextChangedListener { contactForm.firstName = edtFirstName.text.toString() }
        edtLastName.addTextChangedListener { contactForm.lasName = edtLastName.text.toString() }
        edtEmail.addTextChangedListener { contactForm.email = edtEmail.text.toString() }
    }

    override fun showContact(contact: PersonDAO) {
        imgAvatar.loadImage(contact.avatar ?: "")
        edtFirstName.setText(contact.first_name)
        edtLastName.setText(contact.last_name)
        edtEmail.setText(contact.email)
    }

    @OnClick(R.id.btnOk)
    fun onClickSave() {
        presenter.updateContact(contactForm, mContactToDisplay!!)
    }

    override fun backPreviousForUpdateSuccess(newContact: PersonDAO) {
        goBack()
        callbackUpdateSuccess(newContact)
    }

    override fun getTitle(): String = getString(R.string.lb_update_contact_title)

    override fun onDestroy() {
        super.onDestroy()
        mContactToDisplay = null
    }

    companion object {
        private var mContactToDisplay: PersonDAO? = null
        private var callbackUpdateSuccess: (contactUpdated: PersonDAO) -> Unit = {}
        fun setContact(contact: PersonDAO, onUpdated: (contactUpdated: PersonDAO) -> Unit) {
            mContactToDisplay = contact
            callbackUpdateSuccess = onUpdated
        }
    }
}