package com.phuoc.domain.entities

import com.google.gson.annotations.SerializedName

data class TrackingEntity(
    @SerializedName("tracking_id") val id: Int,
    @SerializedName("schedule") val schedule: String,
    @SerializedName("vehicle") val vehicle: String
)
