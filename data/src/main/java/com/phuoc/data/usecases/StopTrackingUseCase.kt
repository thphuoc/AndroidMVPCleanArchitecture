package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.ITrackingService
import com.phuoc.domain.usecases.IStopTrackingUseCase
import io.reactivex.Completable

class StopTrackingUseCase(private val service: ITrackingService) : IStopTrackingUseCase {
    override fun execute(
        trackingId: Int
    ): Completable {
        return service.stopTracking(trackingId).transformCompletable()
    }
}