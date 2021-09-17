package com.example.test.utils

import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ViewStateEnum
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <S> Observable<S>.then(
    vm: BaseViewModel<*>,
    onNext: (it: S) -> Unit = {},
    onCompleted: () -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable ->
        vm.publishState(ViewStateEnum.ERROR, throwable.message ?: "")
    }
) {
    vm.addDisposable(
        this.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onCompleted)
    )
}