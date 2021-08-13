package com.example.test.exts

import com.example.test.data.apimgr.exceptions.InvalidAccessTokenException
import com.example.test.view.base.IView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <S> Single<S>.observe(
    view: IView,
    showLoadingDialog: Boolean = true,
    onSuccess: (it: S) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
): Disposable {
    if (showLoadingDialog) {
        view.showLoadingDialog()
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (showLoadingDialog) {
                view.hideLoadingDialog()
            }
            onSuccess(it)
        }, {
            if (showLoadingDialog) {
                view.hideLoadingDialog()
            }
            onError(it)
        })
    view.addDisposable(disposable)

    return disposable
}

private fun handleError(context: IView, throwable: Throwable) {
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
        context.showErrorDialog(
            message = throwable.message ?: "",
            onYes = {},
            onDismiss = {
            }
        )
    }
}

fun Completable.observe(
    view: IView,
    showLoadingDialog: Boolean = true,
    onCompleted: () -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(view, throwable) }
) {
    if (showLoadingDialog) {
        view.showLoadingDialog()
    }
    view.addDisposable(
        this.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (showLoadingDialog) {
                    view.hideLoadingDialog()
                }
                onCompleted()
            }, {
                if (showLoadingDialog) {
                    view.hideLoadingDialog()
                }
                onError(it)
            })
    )
}