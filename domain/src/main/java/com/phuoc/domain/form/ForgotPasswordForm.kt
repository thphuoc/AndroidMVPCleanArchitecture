package com.phuoc.domain.form

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgotPasswordForm(
    @SerializedName("email") var email: String = ""
) : IPageForm
