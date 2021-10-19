package com.phuoc.domain.utils

object Validator {
    fun isEmailPattern(email: String): Boolean {
        return email.isNotBlank()
    }

    fun isValidPassword(password: String) : Boolean {
        return password.matches(Regex("[a-zA-Z0-9]{6,}"))
    }
}