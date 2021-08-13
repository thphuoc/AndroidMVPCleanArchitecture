package com.example.test.data.usecases

import com.example.test.data.form.ContactForm
import io.reactivex.Completable

class UpdateContactUseCase : BaseUseCase<ContactForm, Completable> {
    override fun execute(input: ContactForm): Completable {
        //TODO: call API update contact
        return Completable.complete()
    }
}