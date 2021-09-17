package com.example.test.view.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.test.R
import com.example.test.data.form.SearchForm
import com.example.test.view.base.viewBinder.LoadMoreViewBinder
import com.example.test.view.exts.buildStateEmptyLayout
import com.example.test.view.exts.buildStateInitLayout
import com.example.test.view.exts.buildStateLoadingLayout
import kotlinx.android.synthetic.main.fragment_state_list.*

abstract class PaginationListFragment<Data> : StateFragment<Data>() {
    override val layoutResId: Int = R.layout.fragment_state_list

    /**
     * This variable in order to indicate load data or not when viewpager focused into current fragment
     */
    open val initStateIconResId = R.drawable.ic_empty_search
    open val initStateTextResId = R.string.hint_input_search

    val searchForm by lazy { SearchForm() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.buildStateEmptyLayout()
        stateLayout?.buildStateInitLayout(customView = {
            it.findViewById<ImageView>(R.id.imgEmpty).setImageResource(initStateIconResId)
            it.findViewById<TextView>(R.id.tvNoResultSearch).setText(initStateTextResId)
        })
        stateLayout?.buildStateLoadingLayout()

        swipeLayout?.setOnRefreshListener {
            placeHolderView.loadingDone()
            placeHolderView.noMoreToLoad()
            searchForm.page = 0
            setupLoadMore()
            viewModel.loadData()
        }
        setupLoadMore()
    }

    private fun setupLoadMore() {
        placeHolderView?.setLoadMoreResolver(LoadMoreViewBinder {
            searchForm.page++
            viewModel.loadData()
        })
    }

    fun showListItems(
        viewBinders: List<Any>,
        appendList: Boolean = false,
        hasMore: Boolean = false
    ) {
        swipeLayout.isRefreshing = false
        placeHolderView.loadingDone()
        if (viewBinders.isEmpty()) {
            stateLayout?.showEmpty()
            return
        } else {
            stateLayout?.showContent()
        }

        if (!appendList) {
            placeHolderView.removeAllViews()
        }
        if (!hasMore) {
            placeHolderView.noMoreToLoad()
        }

        viewBinders.forEach { viewItemToDisplay ->
            placeHolderView.addView(viewItemToDisplay)
        }
    }
}