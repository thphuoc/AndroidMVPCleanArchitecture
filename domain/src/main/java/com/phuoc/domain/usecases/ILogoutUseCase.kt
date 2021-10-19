package com.phuoc.domain.usecases

import io.reactivex.Completable

interface ILogoutUseCase {
    fun execute(): Completable
}