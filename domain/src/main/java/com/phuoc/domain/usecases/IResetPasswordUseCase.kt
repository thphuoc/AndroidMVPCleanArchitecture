package com.phuoc.domain.usecases

import com.phuoc.domain.form.ResetPasswordForm
import io.reactivex.Completable

interface IResetPasswordUseCase : BaseUseCaseInterface<ResetPasswordForm, Completable>