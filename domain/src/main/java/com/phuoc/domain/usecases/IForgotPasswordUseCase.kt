package com.phuoc.domain.usecases

import com.phuoc.domain.form.ForgotPasswordForm
import io.reactivex.Completable

interface IForgotPasswordUseCase : BaseUseCaseInterface<ForgotPasswordForm, Completable>