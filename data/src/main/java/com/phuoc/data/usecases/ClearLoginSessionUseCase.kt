package com.phuoc.data.usecases

import android.content.SharedPreferences
import com.phuoc.domain.usecases.IClearLoginSessionUseCase

class ClearLoginSessionUseCase(private val sharedPref: SharedPreferences) : IClearLoginSessionUseCase {
    override fun execute() {
        sharedPref.edit().clear().apply()
    }
}