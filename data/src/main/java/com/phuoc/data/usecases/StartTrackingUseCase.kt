package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformData
import com.phuoc.data.services.ITrackingService
import com.phuoc.domain.entities.TrackingEntity
import com.phuoc.domain.usecases.IStartTrackingUseCase
import io.reactivex.Single

class StartTrackingUseCase(private val service: ITrackingService) : IStartTrackingUseCase {
    override fun execute(
        schedule: Int,
        vehicle: Int,
        lat: Double,
        lng: Double
    ): Single<TrackingEntity> {
        return service.startTracking(schedule, vehicle, lat, lng).transformData()
    }
}