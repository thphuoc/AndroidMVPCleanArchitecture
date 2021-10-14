package com.phuoc.data.services

import com.phuoc.data.base.HttpResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface IOtpService {
    @POST("/otp/request")
    fun requestOtp(@Body request: com.phuoc.domain.form.RequestOtpForm) : Single<HttpResponse<Any>>

    @POST("/otp/validate")
    fun validateOtp(@Body request: com.phuoc.domain.form.VerifyOtpForm) : Single<HttpResponse<Any>>
}