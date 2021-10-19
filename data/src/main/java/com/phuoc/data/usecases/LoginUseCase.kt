package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformData
import com.phuoc.data.services.IAuthService
import com.phuoc.domain.entities.SessionEntity
import com.phuoc.domain.form.LoginForm
import com.phuoc.domain.usecases.ILoginUseCase
import com.phuoc.domain.utils.Validator
import io.reactivex.Single

class LoginUseCase(private val service: IAuthService) : ILoginUseCase {
    override fun execute(input: LoginForm): Single<SessionEntity> {
        if(!Validator.isEmailPattern(input.email)) {
            return Single.error(Exception("Username invalid"))
        }
        if(!Validator.isValidPassword(input.password)) {
            return Single.error(Exception("Password length should be at least 6 characters"))
        }

        return service.login(
            username = input.email,
            password = input.password
        ).transformData()
    }
}