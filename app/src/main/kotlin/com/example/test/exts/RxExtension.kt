package com.example.test.exts

import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.ViewNavigator
import com.example.test.view.base.ViewStateEnum
import com.phuoc.domain.exceptions.InvalidAccessTokenException
import com.phuoc.domain.exceptions.RemoteException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


private fun handleError(view: BaseViewModel<*>, throwable: Throwable) {
    var showError = true
    throwable.printStackTrace()
    if (throwable is RemoteException && throwable.code==401) {
        showError = false
        view.navigate(ViewNavigator(screen = ScreenEnum.LOGOUT))
    } else {
        throwable.cause?.let { errorCause ->
            if (errorCause is InvalidAccessTokenException) {
                showError = false
            }
        }
        if (showError) {
            view.publishState(ViewStateEnum.ERROR, throwable.message ?: "")
        }
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

fun <T> Single<T>.observe(
    view: BaseViewModel<*>,
    showLoadingDialog: Boolean = true,
    onSuccess: (data: T) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
) {
    if (showLoadingDialog) {
        view.publishState(ViewStateEnum.DIALOG_LOADING)
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onSuccess(it) }, { onError(it) })

    view.addDisposable(
        disposable
    )
}

fun <T> Observable<T>.observe(
    view: BaseViewModel<*>,
    showLoadingDialog: Boolean = true,
    onSuccess: (data: T) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
) {
    if (showLoadingDialog) {
        view.publishState(ViewStateEnum.DIALOG_LOADING)
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onSuccess(it) }, { onError(it) })

    view.addDisposable(
        disposable
    )
}