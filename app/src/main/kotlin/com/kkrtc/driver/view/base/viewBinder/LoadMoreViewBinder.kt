package com.kkrtc.driver.view.base.viewBinder

import com.kkrtc.driver.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.infinite.LoadMore

@Layout(R.layout.view_load_more)
class LoadMoreViewBinder(private val onLoadMore: () -> Unit) {
    @LoadMore
    fun loadMore() {
        onLoadMore()
    }
}