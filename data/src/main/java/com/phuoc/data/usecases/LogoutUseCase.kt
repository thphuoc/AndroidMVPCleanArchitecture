package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.IAuthService
import com.phuoc.domain.usecases.ILogoutUseCase
import io.reactivex.Completable

class LogoutUseCase(private val service: IAuthService) : ILogoutUseCase {
    override fun execute(): Completable {
        return service.logout().transformCompletable()
    }
}