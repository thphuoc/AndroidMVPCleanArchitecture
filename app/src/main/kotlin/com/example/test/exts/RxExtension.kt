package com.example.test.exts

import com.example.test.data.apimgr.exceptions.InvalidAccessTokenException
import com.example.test.view.base.BaseViewModel
import com.example.test.view.base.ViewStateEnum
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <S> Single<S>.observe(
    view: BaseViewModel<*>,
    showLoadingDialog: Boolean = true,
    onSuccess: (it: S) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
): Disposable {
    if (showLoadingDialog) {
        view.publishState(viewState = ViewStateEnum.DIALOG_LOADING)
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (showLoadingDialog) {
                view.publishState(viewState = ViewStateEnum.SHOW_DATA)
            }
            onSuccess(it)
        }, {
            if (showLoadingDialog) {
                view.publishState(viewState = ViewStateEnum.SHOW_DATA)
            }
            onError(it)
        })
    view.addDisposable(disposable)

    return disposable
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