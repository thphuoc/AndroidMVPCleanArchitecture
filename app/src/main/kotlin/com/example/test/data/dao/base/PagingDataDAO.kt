package com.example.test.data.dao.base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PagingDataDAO<E>(
    @SerializedName("data")
    val data: List<E>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("total")
    val totalElements: Int? = 0
) {
    fun hasMore() = (totalPages?:0) > (page?:0)
}