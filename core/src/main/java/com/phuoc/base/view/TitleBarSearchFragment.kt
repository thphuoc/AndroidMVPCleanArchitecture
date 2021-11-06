package com.phuoc.base.view

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.example.test.R
import com.example.test.view.exts.goBack
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