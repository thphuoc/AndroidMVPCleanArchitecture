package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.IOtpService
import com.phuoc.domain.form.RequestOtpForm
import com.phuoc.domain.usecases.RequestOtpInterface
import com.phuoc.domain.utils.Validator
import io.reactivex.Completable

class RequestOtpUseCase(private val service: IOtpService) : RequestOtpInterface {
    override fun execute(input: RequestOtpForm): Completable {
        return if(Validator.isEmailPattern(input.email)) {
            return service.requestOtp(input).transformCompletable()
        } else {
            Completable.error(Exception("Email invalid"))
        }
    }
}