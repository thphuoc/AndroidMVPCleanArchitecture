package com.phuoc.data.rxmapper

import com.phuoc.data.base.HttpResponse
import io.reactivex.Completable
import io.reactivex.Single

fun <I> Single<HttpResponse<I>>.transformCompletable(onResult: (data: I) -> Unit = {}): Completable {
    return this.flatMapCompletable { response ->
        if (response.isSuccess()) {
            response.data?.apply { onResult(this) }
            Completable.complete()
        } else {
            throw Exception(response.message ?: "")
        }
    }
}

fun <I> Single<HttpResponse<I>>.transformData(): Single<I> {
    return this.flatMap { response ->
        if (response.isSuccess() && response.data != null) {
            Single.just(response.data)
        } else {
            throw Exception(response.message ?: "")
        }
    }
}