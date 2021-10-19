package com.phuoc.data.services

import com.phuoc.data.base.HttpResponse
import com.phuoc.domain.entities.TrackingEntity
import io.reactivex.Single
import retrofit2.http.*

interface ITrackingService {
    @FormUrlEncoded
    @POST("api/v1/driver/tracking")
    @Headers("Accept: application/json")
    fun startTracking(
        @Field("schedule") schedule: Int,
        @Field("vehicle") vehicle: Int,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double
    ): Single<HttpResponse<TrackingEntity>>

    @FormUrlEncoded
    @PUT("api/v1/driver/tracking/{tracking_id}")
    @Headers("Accept: application/json")
    fun updateTracking(
        @Path("tracking_id") tracking_id: Int,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double
    ): Single<HttpResponse<Any>>

    @FormUrlEncoded
    @PUT("api/v1/driver/tracking/{tracking_id}/end")
    @Headers("Accept: application/json")
    fun stopTracking(@Path("tracking_id") tracking_id: Int): Single<HttpResponse<Any>>
}