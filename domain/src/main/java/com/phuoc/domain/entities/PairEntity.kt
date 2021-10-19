package com.phuoc.domain.entities

import com.google.gson.annotations.SerializedName

data class PairEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("value") val value: String
)
