package com.phuoc.domain.usecases

import io.reactivex.Completable

interface IStopTrackingUseCase {
    fun execute(trackingId: Int) : Completable
}