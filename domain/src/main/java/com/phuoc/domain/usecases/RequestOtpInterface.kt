package com.phuoc.domain.usecases

import com.phuoc.domain.form.RequestOtpForm
import io.reactivex.Completable

interface RequestOtpInterface : BaseUseCaseInterface<RequestOtpForm, Completable>