package com.thphuoc.theme_core.enums

import android.content.Context
import com.thphuoc.theme_core.R

enum class MaritalStatusEnum(val value: String, private val textResId: Int) {
    SINGLE("SINGLE", R.string.lb_marital_status_single),
    MARRIED("MARRIED", R.string.lb_marital_status_married);

    fun getText(context: Context): String {
        return context.getString(textResId)
    }

    companion object {
        fun getType(type: String): MaritalStatusEnum {
            return values().firstOrNull { it.value == type } ?: SINGLE
        }
    }
}