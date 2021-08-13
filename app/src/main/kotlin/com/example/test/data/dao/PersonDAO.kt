package com.example.test.data.dao

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PersonDAO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("first_name") var first_name: String? = null,
    @SerializedName("last_name") var last_name: String? = null,
    @SerializedName("avatar") val avatar: String? = null
)