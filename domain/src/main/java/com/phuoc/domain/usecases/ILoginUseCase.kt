package com.phuoc.domain.usecases

import com.phuoc.domain.entities.SessionEntity
import com.phuoc.domain.form.LoginForm
import io.reactivex.Single

interface ILoginUseCase : BaseUseCaseInterface<LoginForm, Single<SessionEntity>>