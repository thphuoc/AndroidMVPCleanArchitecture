package com.example.test.data.services

import com.example.test.data.dao.base.HttpResponse
import com.example.test.data.form.RequestOtpForm
import com.example.test.data.form.VerifyOtpForm
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface IOtpService {
    @POST("/otp/request")
    fun requestOtp(@Body request: RequestOtpForm) : Single<HttpResponse<Any>>

    @POST("/otp/validate")
    fun validateOtp(@Body request: VerifyOtpForm) : Single<HttpResponse<Any>>
}