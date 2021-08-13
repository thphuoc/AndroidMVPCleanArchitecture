package com.example.test.view.screens.home.viewBinder

import android.widget.ImageView
import android.widget.TextView
import com.example.test.R
import com.example.test.data.dao.PersonDAO
import com.example.test.view.base.StateFragment
import com.example.test.view.exts.goNext
import com.example.test.view.exts.loadImage
import com.example.test.view.screens.contact.ContactActivity
import com.example.test.view.screens.contact.ContactFragment
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.view_person_item)
class ContactItemViewBinder(private val context: StateFragment, private var contact: PersonDAO) {

    @View(R.id.imgAvatar)
    lateinit var imgAvatar: ImageView

    @View(R.id.tvName)
    lateinit var tvName: TextView

    @View(R.id.tvEmail)
    lateinit var tvEmail: TextView

    @Resolve
    fun onResolve() {
        showContactInfo(contact)
    }

    private fun showContactInfo(contactToDisplay: PersonDAO) {
        imgAvatar.loadImage(contactToDisplay.avatar ?: "")
        tvName.text = "${contactToDisplay.first_name} ${contactToDisplay.last_name}"
        tvEmail.text = contactToDisplay.email
    }

    @Click(R.id.ctItem)
    fun onClickItem() {
        ContactFragment.setContact(contact = contact, onUpdated = { contactHasUpdated ->
            contact = contactHasUpdated
            showContactInfo(contactHasUpdated)
        })
        context.goNext(ContactActivity::class.java)
    }
}