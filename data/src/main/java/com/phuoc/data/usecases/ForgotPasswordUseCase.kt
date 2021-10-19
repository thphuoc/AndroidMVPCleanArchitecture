package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.IAuthService
import com.phuoc.domain.form.ForgotPasswordForm
import com.phuoc.domain.usecases.IForgotPasswordUseCase
import com.phuoc.domain.utils.Validator
import io.reactivex.Completable

class ForgotPasswordUseCase(private val service: IAuthService) : IForgotPasswordUseCase {
    override fun execute(input: ForgotPasswordForm): Completable {
        if(!Validator.isEmailPattern(input.email)) {
            return Completable.error(Exception("Username invalid"))
        }

        return service.forgotPassword(username = input.email).transformCompletable()
    }
}