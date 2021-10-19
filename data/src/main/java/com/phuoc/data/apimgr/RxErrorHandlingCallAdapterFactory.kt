package com.phuoc.data.apimgr

import com.google.gson.Gson
import com.phuoc.data.BuildConfig
import com.phuoc.data.base.HttpResponse
import com.phuoc.domain.exceptions.InvalidAccessTokenException
import com.phuoc.domain.exceptions.RemoteException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

internal class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        @Suppress("UNCHECKED_CAST")
        return RxCallAdapterWrapper(
            original.get(
                returnType,
                annotations,
                retrofit
            ) as CallAdapter<Any, Any>
        )
    }

    private class RxCallAdapterWrapper(private val wrapped: CallAdapter<Any, Any>) :
        CallAdapter<Any, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<Any>): Any {
            val result = wrapped.adapt(call)
            if (result is Single<*>) {
                return result.doOnSuccess { filterGenericUnhappyCasesOnSuccess(it) }
                    .onErrorResumeNext { Single.error(asRetrofitException(it)) }
            }

            if (result is Observable<*>) {
                return result
                    .doOnNext { filterGenericUnhappyCasesOnSuccess(it) }
                    .onErrorResumeNext(Function { Observable.error(asRetrofitException(it)) })
            }

            return if (result is Completable) {
                result.onErrorResumeNext { Completable.error(asRetrofitException(it)) }
            } else result
        }

        private fun filterGenericUnhappyCasesOnSuccess(response: Any) {
            if (response is HttpResponse<*> && response.isUnauthorized()) {
                throw InvalidAccessTokenException()
            }
        }

        private fun asRetrofitException(throwable: Throwable): Throwable {
            throwable.printStackTrace()
            return when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    val bodyAsString = response?.errorBody()?.string() ?: ""
                    try {

                        if(response?.code() == 401) {
                            throw InvalidAccessTokenException()
                        }

                        val responseModel = Gson().fromJson(
                            bodyAsString, HttpResponse::class.java
                        )

                        var message = responseModel.message
                        if (!responseModel.errors.isNullOrEmpty()) {
                            message = responseModel.errors.entries.first().value.firstOrNull()
                        }

                        return RemoteException(
                            response?.code() ?: 0,
                            message ?: responseModel.toString()
                        )
                    } catch (e: Exception) {
                        if (BuildConfig.DEBUG) {
                            RemoteException(
                                response?.code() ?: 0,
                                e.message ?: e.toString()
                            )
                        } else {
                            RemoteException(
                                response?.code() ?: 0,
                                "Oops!"
                            )
                        }
                    }
                }

                else -> RemoteException(0, "Oops!")
            }
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }
}