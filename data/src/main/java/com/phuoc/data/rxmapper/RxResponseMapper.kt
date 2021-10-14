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