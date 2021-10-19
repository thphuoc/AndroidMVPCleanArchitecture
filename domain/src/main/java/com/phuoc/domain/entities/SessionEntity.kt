package com.phuoc.domain.entities

import com.google.gson.annotations.SerializedName

data class SessionEntity(
    @SerializedName("token") val token: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String
)
