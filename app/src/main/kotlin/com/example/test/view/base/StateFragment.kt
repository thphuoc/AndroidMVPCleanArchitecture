package com.example.test.view.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.test.R
import com.example.test.view.exts.goBack
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_error.view.*
import kotlinx.android.synthetic.main.fragment_base.*
import java.io.File

/**
 * StateFragment is a base fragment handle for 5 generic UI states
 * 1. On View init -> show init view
 * 2. On Loading data -> show progress
 * 3. On No data -> show empty
 * 4. On has data -> show content view
 * 5. On Error when load data at the first time -> show error view
 */
abstract class StateFragment : Fragment(), IView {
    open val layoutResId: Int = R.layout.fragment_base
    lateinit var unBinder: Unbinder
    private var loadingDialog: MaterialDialog? = null
    private val disposables = CompositeDisposable()
    abstract val presenter: BasePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = LayoutInflater
            .from(context)
            .inflate(layoutResId, container, false)
        unBinder = ButterKnife.bind(this, contentView)
        return contentView
    }

    override fun showLoadingView() {
        stateLayout?.showProgress()
    }

    override fun showEmptyView() {
        stateLayout?.showEmpty()
    }

    override fun showErrorView(message: String) {
        stateLayout?.showError(message)
    }

    override fun showContentView() {
        stateLayout?.showContent()
    }

    override fun showInitView() {
        stateLayout?.showOffline()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun fitStatusBar(view: View) {
        val topMargin = getStatusBarHeight()
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = topMargin
    }

    fun fitNavigationBar(view: View) {
        val bottomMargin = getBottomNavHeight()
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = bottomMargin
    }

    private fun showLoading(context: Context): MaterialDialog {
        return context.run {
            MaterialDialog(this).show {
                customView(viewRes = R.layout.dialog_progress)
                cancelable(false)
                view.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    override fun getDataDir(): File {
        return File(requireContext().applicationInfo.dataDir)
    }

    override fun showErrorDialog(
        message: String,
        cancelable: Boolean,
        onYes: () -> Unit,
        onDismiss: () -> Unit
    ): MaterialDialog? {
        return context?.run {
            MaterialDialog(this).show {
                customView(viewRes = R.layout.dialog_error, scrollable = false)
                view.btnDialogPositive.setOnClickListener {
                    onYes()
                    dismiss()
                    onDismiss()
                }
                view.setBackgroundColor(Color.TRANSPARENT)
                view.btnDialogPositive.setText(R.string.btn_ok)
                view.tvDialogMessage.text = message
                cancelable(cancelable)
            }
        }
    }

    override fun showLoadingDialog() {
        loadingDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        } ?: run {
            loadingDialog = showLoading(requireContext())
        }
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        disposables.clear()
        unBinder.unbind()
        hideLoadingDialog()
    }

    fun getBottomNavHeight(): Int {
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun backPrevious() {
        goBack()
    }
}