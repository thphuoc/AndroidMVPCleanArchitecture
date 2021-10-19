package com.phuoc.data.base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class HttpResponse<E>(default: E? = null, defaultStatus: String = "success") {
    @SerializedName("status")
    var status: String? = defaultStatus

    @SerializedName("message")
    val message: String? = null

    @SerializedName("errors")
    val errors: Map<String, List<String>>? = null

    @SerializedName("data")
    val data: E? = default

    fun isUnauthorized(): Boolean = message == "Invalid Credentials"
    fun isSuccess(): Boolean = status  == "success"
}