package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformData
import com.phuoc.data.services.IGetVehicleService
import com.phuoc.domain.entities.PairEntity
import com.phuoc.domain.usecases.IGetVehicleUseCase
import io.reactivex.Single

class GetVehicleUseCase(private val service: IGetVehicleService) : IGetVehicleUseCase {
    override fun execute(q: String): Single<List<PairEntity>> {
        return service.getVehicles(q).transformData()
    }
}