package com.phuoc.domain.form

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResetPasswordForm(
    @SerializedName("current_password") var currentPassword: String = "",
    @SerializedName("new_password") var newPassword: String = "",
    @SerializedName("new_confirm_password") var newConfirmPassword: String = ""
) : IPageForm
