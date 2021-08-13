package com.example.test.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.test.R
import com.example.test.view.exts.goBack
import kotlinx.android.synthetic.main.fragment_title_bar.view.*
import kotlinx.android.synthetic.main.view_title_bar.*

abstract class TitleBarFragment : PaginationListFragment() {
    override val layoutResId: Int = R.layout.fragment_title_bar
    abstract val containerLayoutResId: Int

    abstract fun getTitle(): String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(requireContext()).inflate(layoutResId, null, false)
        LayoutInflater.from(requireContext())
            .inflate(containerLayoutResId, view.container, true)
        unBinder = ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitleBarLabel?.text = getTitle()
        fitStatusBar(imgBack)
    }

    @OnClick(R.id.imgBack)
    fun onClickBack() {
        goBack()
    }
}