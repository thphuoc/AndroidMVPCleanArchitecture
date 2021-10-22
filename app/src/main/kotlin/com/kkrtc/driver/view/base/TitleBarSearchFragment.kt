package com.kkrtc.driver.view.base

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import butterknife.OnClick
import com.kkrtc.driver.R
import com.kkrtc.driver.view.exts.goBack
import kotlinx.android.synthetic.main.view_header_search.*

abstract class TitleBarSearchFragment<Data> : PaginationListFragment<Data>() {
    override val layoutResId: Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(imgBack)
        edtSearch?.addTextChangedListener {
            searchForm.searchText = edtSearch.text.toString()
            searchForm.page = 0
            viewModel.loadData()
        }
    }

    @OnClick(R.id.imgBack)
    open fun onClickBack() {
        goBack()
    }
}