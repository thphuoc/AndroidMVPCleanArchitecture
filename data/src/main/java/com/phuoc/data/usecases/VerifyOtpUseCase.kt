package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.IOtpService
import com.phuoc.domain.form.VerifyOtpForm
import com.phuoc.domain.usecases.BaseUseCaseInterface
import com.phuoc.domain.utils.Validator
import io.reactivex.Completable

class VerifyOtpUseCase(private val service: IOtpService) : BaseUseCaseInterface<VerifyOtpForm, Completable> {
    override fun execute(input: VerifyOtpForm): Completable {
        return if (Validator.isOtpPattern(input.otp)) {
            if (input.otp == "1234") {
                return service.validateOtp(input).transformCompletable()
            } else {
                Completable.error(Exception("OTP is invalid"))
            }
        } else {
            Completable.error(Exception("Otp must be 4 digits"))
        }
    }
}