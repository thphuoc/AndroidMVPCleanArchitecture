package com.phuoc.domain.usecases

import com.phuoc.domain.entities.SessionEntity

interface ICacheLoginSessionUseCase {
    fun execute(session:SessionEntity)
}