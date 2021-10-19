package com.phuoc.data.services

import com.phuoc.data.base.HttpResponse
import com.phuoc.domain.entities.PairEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IGetVehicleService {
    @GET("/api/v1/driver/get-vehicles")
    @Headers("Accept: application/json")
    fun getVehicles(@Query("q") q: String = "") : Single<HttpResponse<List<PairEntity>>>

    @GET("api/v1/driver/get-schedules")
    @Headers("Accept: application/json")
    fun getSchedulers(@Query("q") q: String = "") : Single<HttpResponse<List<PairEntity>>>
}