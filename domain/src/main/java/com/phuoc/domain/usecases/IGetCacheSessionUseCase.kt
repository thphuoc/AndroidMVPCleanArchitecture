package com.phuoc.domain.usecases

import com.phuoc.domain.entities.SessionEntity

interface IGetCacheSessionUseCase {
    fun execute() : SessionEntity?
}