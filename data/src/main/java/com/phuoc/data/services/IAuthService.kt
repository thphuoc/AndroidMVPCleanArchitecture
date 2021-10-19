package com.phuoc.data.services

import com.phuoc.data.base.HttpResponse
import com.phuoc.domain.entities.SessionEntity
import io.reactivex.Single
import retrofit2.http.*

interface IAuthService {
    @FormUrlEncoded
    @POST("api/v1/driver/account/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("device_name") device_name: String = "Android"
    ): Single<HttpResponse<SessionEntity>>

    @FormUrlEncoded
    @POST("api/v1/driver/account/forgot-password")
    fun forgotPassword(@Field("username") username: String): Single<HttpResponse<Any>>

    @FormUrlEncoded
    @POST("api/v1/driver/account/reset-password")
    @Headers("Accept: application/json")
    fun resetPassword(
        @Field("current_password") current_password: String,
        @Field("new_password") new_password: String,
        @Field("new_confirm_password") new_confirm_password: String
    ): Single<HttpResponse<Any>>

    @GET("api/v1/driver/account/logout")
    fun logout(): Single<HttpResponse<Any>>
}