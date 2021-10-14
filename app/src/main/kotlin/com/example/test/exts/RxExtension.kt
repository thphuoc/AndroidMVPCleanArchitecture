package com.example.test.exts

import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ViewStateEnum
import com.phuoc.domain.exceptions.InvalidAccessTokenException
import io.reactivex.Completable
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

private fun handleError(view: BaseViewModel<*>, throwable: Throwable) {
    var showError = true
    throwable.printStackTrace()
    if (throwable is InvalidAccessTokenException) {
        showError = false
    }
    throwable.cause?.let { errorCause ->
        if (errorCause is InvalidAccessTokenException) {
            showError = false
        }
    }
    if (showError) {
        view.publishState(ViewStateEnum.ERROR, throwable.message ?: "")
    }
}

fun Completable.observe(
    view: BaseViewModel<*>,
    showLoadingDialog: Boolean = true,
    onCompleted: () -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
) {
    if (showLoadingDialog) {
        view.publishState(ViewStateEnum.DIALOG_LOADING)
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onCompleted() }, { onError(it) })

    view.addDisposable(
        disposable
    )
}