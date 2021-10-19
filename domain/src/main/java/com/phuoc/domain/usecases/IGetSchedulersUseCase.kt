package com.phuoc.domain.usecases

import com.phuoc.domain.entities.PairEntity
import io.reactivex.Single

interface IGetSchedulersUseCase {
    fun execute(q: String) : Single<List<PairEntity>>
}