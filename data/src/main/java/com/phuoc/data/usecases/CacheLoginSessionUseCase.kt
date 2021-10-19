package com.phuoc.data.usecases

import android.content.SharedPreferences
import com.phuoc.domain.entities.SessionEntity
import com.phuoc.domain.usecases.ICacheLoginSessionUseCase

class CacheLoginSessionUseCase(private val sharedPref: SharedPreferences) :
    ICacheLoginSessionUseCase {
    override fun execute(session: SessionEntity) {
        sharedPref.edit()
            .putString("session", session.token)
            .putString("name", session.name)
            .putString("username", session.username)
            .apply()
    }
}