package com.example.test.view.base

import com.afollestad.materialdialogs.MaterialDialog
import io.reactivex.disposables.Disposable
import java.io.File

interface IView {
    fun addDisposable(disposable: Disposable)
    fun showLoadingView()
    fun showEmptyView()
    fun showErrorView(message: String = "Error")
    fun showContentView()
    fun showInitView()

    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showErrorDialog(
        message: String,
        cancelable: Boolean = true,
        onYes: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ): MaterialDialog?

    fun getDataDir(): File

    fun backPrevious()
}