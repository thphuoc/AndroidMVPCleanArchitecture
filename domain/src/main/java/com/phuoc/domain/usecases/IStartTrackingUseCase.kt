package com.phuoc.domain.usecases

import com.phuoc.domain.entities.TrackingEntity
import io.reactivex.Single

interface IStartTrackingUseCase {
    fun execute(schedule: Int, vehicle: Int, lat: Double, lng: Double) : Single<TrackingEntity>
}