package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformData
import com.phuoc.data.services.IGetVehicleService
import com.phuoc.domain.entities.PairEntity
import com.phuoc.domain.usecases.IGetSchedulersUseCase
import io.reactivex.Single

class GetSchedulersUseCase(private val service: IGetVehicleService) : IGetSchedulersUseCase {
    override fun execute(q: String): Single<List<PairEntity>> {
        return service.getSchedulers(q).transformData()
    }
}