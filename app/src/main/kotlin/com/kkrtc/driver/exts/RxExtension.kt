package com.kkrtc.driver.exts

import com.kkrtc.driver.view.base.BaseViewModel
import com.kkrtc.driver.view.base.ScreenEnum
import com.kkrtc.driver.view.base.ViewNavigator
import com.kkrtc.driver.view.base.ViewStateEnum
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
    if (throwable is RemoteException) {
        if (throwable.code == 401) {
            showError = false
            view.navigate(ViewNavigator(screen = ScreenEnum.LOGOUT))
        } else {
            throwable.cause?.let { errorCause ->
                if (errorCause is InvalidAccessTokenException) {
                    showError = false
                }
            }
            if (showError) {
                view.publishState(
                    ViewStateEnum.ERROR,
                    title = throwable.title,
                    throwable.message ?: ""
                )
            }
        }
    } else {
        throwable.cause?.let { errorCause ->
            if (errorCause is InvalidAccessTokenException) {
                showError = false
            }
        }
        if (showError) {
            view.publishState(
                ViewStateEnum.ERROR,
                title = "",
                throwable.message ?: ""
            )
        }
    }
}

fun Completable.observe(
    view: BaseViewModel<*>,
    showLoadingDialog: Boolean = true,
    onCompleted: () -> Unit = {view.publishState(ViewStateEnum.DISMISS_DIALOG)},
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
    onSuccess: (data: T) -> Unit = {view.publishState(ViewStateEnum.DISMISS_DIALOG)},
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
    onSuccess: (data: T) -> Unit = {view.publishState(ViewStateEnum.DISMISS_DIALOG)},
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