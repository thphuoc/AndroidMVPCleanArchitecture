package com.phuoc.domain.form

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginForm(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = ""
) : IPageForm
