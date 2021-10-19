package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.ITrackingService
import com.phuoc.domain.usecases.IUpdateTrackingUseCase
import io.reactivex.Completable

class UpdateTrackingUseCase(private val service: ITrackingService) : IUpdateTrackingUseCase {
    override fun execute(
        trackingId: Int,
        lat: Double,
        lng: Double
    ): Completable {
        return service.updateTracking(trackingId, lat, lng).transformCompletable()
    }
}