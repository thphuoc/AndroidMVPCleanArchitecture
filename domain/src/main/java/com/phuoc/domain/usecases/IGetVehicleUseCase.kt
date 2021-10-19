package com.phuoc.domain.usecases

import com.phuoc.domain.entities.PairEntity
import io.reactivex.Single

interface IGetVehicleUseCase {
    fun execute(q: String) : Single<List<PairEntity>>
}