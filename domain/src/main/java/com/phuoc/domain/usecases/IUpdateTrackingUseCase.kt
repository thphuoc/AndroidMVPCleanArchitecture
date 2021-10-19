package com.phuoc.domain.usecases

import io.reactivex.Completable

interface IUpdateTrackingUseCase {
    fun execute(trackingId: Int,
                lat: Double,
                lng: Double) : Completable
}