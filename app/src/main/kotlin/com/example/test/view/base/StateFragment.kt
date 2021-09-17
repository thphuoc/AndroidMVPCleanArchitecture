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
import kotlinx.android.synthetic.main.dialog_error.view.*
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * StateFragment is a base fragment handle for 5 generic UI states
 * 1. On View init -> show init view
 * 2. On Loading data -> show progress
 * 3. On No data -> show empty
 * 4. On has data -> show content view
 * 5. On Error when load data at the first time -> show error view
 */
abstract class StateFragment<Data> : Fragment() {
    open val layoutResId: Int = R.layout.fragment_base
    lateinit var unBinder: Unbinder
    private var loadingDialog: MaterialDialog? = null
    abstract val viewModel: BaseViewModel<Data>

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

    open fun showLoadingView() {
        stateLayout?.showProgress()
    }

    open fun showEmptyView() {
        stateLayout?.showEmpty()
    }

    open fun showErrorView(message: String) {
        stateLayout?.showError(message)
    }

    open fun showContentView() {
        stateLayout?.showContent()
    }

    open fun showInitView() {
        stateLayout?.showOffline()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        viewModel.viewStateLiveData.observe(viewLifecycleOwner, { viewState ->
            handleViewState(viewState)
        })

        viewModel.navigatorState.observe(viewLifecycleOwner, { navigator ->
            hideLoadingDialog()
            when (navigator.screen) {
                ScreenEnum.GO_BACK -> goBack()
                else -> navigate(navigator)
            }
        })
    }

    open fun handleViewState(viewState: ViewState<Data>) {
        hideLoadingDialog()
        when (viewState.viewState) {
            ViewStateEnum.INIT -> showInitView()
            ViewStateEnum.PAGE_LOADING -> showLoadingView()
            ViewStateEnum.DIALOG_LOADING -> showLoadingDialog()
            ViewStateEnum.ERROR -> showErrorDialog(
                message = viewState.errorMessage,
                cancelable = false,
                onYes = {},
                onDismiss = {})
            ViewStateEnum.NO_DATA -> showEmptyView()
            else -> {
                showContentView()
                showData(viewState.data)
            }
        }
    }

    open fun showData(data: Data?) {
    }

    open fun navigate(viewNavigator: ViewNavigator) {
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

    open fun showErrorDialog(
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

    open fun showLoadingDialog() {
        loadingDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        } ?: run {
            loadingDialog = showLoading(requireContext())
        }
    }

    open fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
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

    open fun backPrevious() {
        goBack()
    }
}