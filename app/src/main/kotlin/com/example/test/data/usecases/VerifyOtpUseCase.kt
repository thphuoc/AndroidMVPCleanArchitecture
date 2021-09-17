package com.example.test.data.usecases

import com.example.test.data.form.VerifyOtpForm
import com.example.test.data.rxmapper.transformCompletable
import com.example.test.data.services.IOtpService
import com.example.test.utils.Validator
import io.reactivex.Completable

class VerifyOtpUseCase(private val service:IOtpService) : BaseUseCase<VerifyOtpForm, Completable> {
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