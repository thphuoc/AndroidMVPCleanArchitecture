package com.phuoc.domain.utils

object Validator {
    fun isEmailPattern(email: String): Boolean {
        return email.endsWith("@positivethinking.tech") && email.split("@").size == 2
    }

    fun isOtpPattern(otp: String): Boolean {
        return otp.length == 4
    }
}