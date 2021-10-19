package com.example.test.view.bottomsheets

import android.widget.TextView
import com.example.test.R
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.phuoc.domain.entities.PairEntity

@Layout(R.layout.view_simple_list_item)
class SimpleListItemViewBinder(
    private val item: PairEntity,
    private val onItemClick: (item: PairEntity) -> Unit
) {

    @View(R.id.tvItemText)
    lateinit var tvItemText: TextView

    @Resolve
    fun onResolve() {
        tvItemText.text = item.value
    }

    @Click(R.id.tvItemText)
    fun onClickItem() {
        onItemClick(item)
    }
}