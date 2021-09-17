package com.example.test.data.usecases

import com.example.test.data.form.RequestOtpForm
import com.example.test.data.rxmapper.transformCompletable
import com.example.test.data.services.IOtpService
import com.example.test.utils.Validator
import io.reactivex.Completable

class RequestOtpUseCase(private val service: IOtpService) : BaseUseCase<RequestOtpForm, Completable> {
    override fun execute(input: RequestOtpForm): Completable {
        return if(Validator.isEmailPattern(input.email)) {
            return service.requestOtp(input).transformCompletable()
        } else {
            Completable.error(Exception("Email invalid"))
        }
    }
}