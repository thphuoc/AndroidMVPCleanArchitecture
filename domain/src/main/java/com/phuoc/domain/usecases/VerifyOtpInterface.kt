package com.phuoc.domain.usecases

import com.phuoc.domain.form.VerifyOtpForm
import io.reactivex.Completable

interface VerifyOtpInterface : BaseUseCaseInterface<VerifyOtpForm, Completable>