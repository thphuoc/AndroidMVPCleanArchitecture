package com.example.test.data.form

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyOtpForm(
    @SerializedName("otp") var otp: String = ""
): IPageForm