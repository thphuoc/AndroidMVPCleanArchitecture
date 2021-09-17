package com.example.test.data.form

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestOtpForm(
    @SerializedName("email") var email: String = ""
): IPageForm