package com.phuoc.data.usecases

import android.content.SharedPreferences
import com.phuoc.domain.entities.SessionEntity
import com.phuoc.domain.usecases.IGetCacheSessionUseCase

class GetCacheSessionUseCase(private val sharedPref: SharedPreferences) : IGetCacheSessionUseCase {
    override fun execute(): SessionEntity? {
        val session = sharedPref.getString("session", "")
        val name = sharedPref.getString("name", "")
        val username = sharedPref.getString("username", "")

        return session?.run {
            SessionEntity(
                token = session,
                name = name ?: "",
                username = username ?: ""
            )
        }
    }
}