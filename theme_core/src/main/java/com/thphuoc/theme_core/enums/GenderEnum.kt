package com.thphuoc.theme_core.enums

import android.content.Context
import com.thphuoc.theme_core.R

enum class GenderEnum(val value: String, val textResId: Int) {
    MALE("M", R.string.lb_update_profile_gender_male),
    FEMALE("F", R.string.lb_update_profile_gender_female),
    OTHER("OTHER", R.string.lb_update_profile_gender_other);

    fun getText(context: Context): String {
        return context.getString(textResId)
    }

    companion object {
        fun getType(type: String?): GenderEnum {
            return values().firstOrNull { it.value == type } ?: MALE
        }
    }
}